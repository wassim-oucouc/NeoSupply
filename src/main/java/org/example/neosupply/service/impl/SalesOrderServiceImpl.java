package org.example.neosupply.service.impl;

import jakarta.transaction.Transactional;
import org.example.neosupply.dto.request.*;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.entity.InventoryMovement;
import org.example.neosupply.entity.SalesOrder;
import org.example.neosupply.entity.SalesOrderLine;
import org.example.neosupply.enumeration.MovementType;
import org.example.neosupply.enumeration.SOStatus;
import org.example.neosupply.enumeration.ShipmentStatus;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.mapper.SalesOrderMapper;
import org.example.neosupply.repository.CarrierRepository;
import org.example.neosupply.repository.SalesOrderRepository;
import org.example.neosupply.service.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final InventoryService inventoryService;
    private final SalesOrderMapper salesOrderMapper;
    private final PurchaseOrderService purchaseOrderService;
    private final InventoryMovementService inventoryMovementService;
    private final ShipmentService shipmentService;
    private final CarrierService carrierService;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository,
                                 InventoryService inventoryService,
                                 SalesOrderMapper salesOrderMapper,
                                 PurchaseOrderService purchaseOrderService, InventoryMovementService inventoryMovementService, ShipmentService shipmentService, CarrierService carrierService) {
        this.salesOrderRepository = salesOrderRepository;
        this.inventoryService = inventoryService;
        this.salesOrderMapper = salesOrderMapper;
        this.purchaseOrderService = purchaseOrderService;
        this.inventoryMovementService = inventoryMovementService;
        this.shipmentService = shipmentService;
        this.carrierService = carrierService;
    }

    @Transactional
    public SalesOrderDtoResponse createSalesOrder(SalesOrderDTO salesOrderDTO) {
        SalesOrder salesOrder = this.salesOrderMapper.toEntity(salesOrderDTO);

        List<SalesOrderLine> salesOrderLines = salesOrder.getSalesOrderLines();

        for (SalesOrderLine salesOrderLine : salesOrderLines) {
            salesOrderLine.setSalesOrder(salesOrder);

            Integer quantity = this.inventoryService.getProductQuantityByWarehouse(
                    salesOrder.getWarehouse().getId(),salesOrderLine.getProduct().getId()
            );

            InventoryDtoResponse inventoryDtoResponseFound =
                    this.inventoryService
                            .findInventoryByProductIdAndWarehouseId(
                                    salesOrderLine.getProduct().getId(),
                                    salesOrder.getWarehouse().getId()
                            )
                            .orElseThrow(() -> new InventoryNotFoudException("Inventory Not Found"));

            if (quantity < salesOrderLine.getQuantity()) {
                InventoryDtoResponse inventoryDtoResponse =
                        this.inventoryService.getInventoryByProductId(salesOrderLine.getProduct().getId());

                int remainingQty = salesOrderLine.getQuantity();

                if (inventoryDtoResponse.getQuantityOnHand() >= remainingQty) {
                    inventoryDtoResponse.setQuantityOnHand(
                            inventoryDtoResponse.getQuantityOnHand() - salesOrderLine.getQuantity()
                    );

                    inventoryDtoResponseFound.setQuantityReserved(
                            inventoryDtoResponseFound.getQuantityReserved() + salesOrderLine.getQuantity()
                    );

                    InventoryMovementDTO inventoryMovementDTO = InventoryMovementDTO.builder().movementDate(LocalDateTime.now()).quantity(salesOrderLine.getQuantity()).productId(salesOrderLine.getProduct().getId()).warehouseId(salesOrder.getWarehouse().getId()).type(MovementType.OUTBOUND).build();
                    this.inventoryMovementService.createInventoryMovement(inventoryMovementDTO);

                    salesOrder.setStatus(SOStatus.RESERVED);
                    remainingQty = 0;
                } else {
                    salesOrderLine.setQuantityToOrder(
                            salesOrderLine.getQuantity() - inventoryDtoResponse.getQuantityOnHand()
                    );
                    salesOrder.setStatus(SOStatus.CREATED);
                }
            }
        }

        SalesOrder salesOrderCreated = this.salesOrderRepository.save(salesOrder);
        return this.salesOrderMapper.toDtoResponse(salesOrderCreated);
    }



    @Override
    @Transactional
    public SalesOrderDtoResponse approveSalesOrder(Long salesOrderId, Long carrierId) {
        SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new RuntimeException("Sales Order not found with id: " + salesOrderId));

        if (salesOrder.getStatus() == SOStatus.RESERVED) {
            throw new IllegalStateException("Sales Order is already approved/reserved.");
        }

        salesOrder.setStatus(SOStatus.RESERVED);

        salesOrder.getSalesOrderLines().forEach(line -> {
            ShipmentDTO shipmentDTO = ShipmentDTO.builder()
                    .shipmentStatus(ShipmentStatus.PLANNED)
                    .trackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8))
                    .build();

            shipmentService.createShipment(shipmentDTO);
        });

        SalesOrder savedOrder = salesOrderRepository.save(salesOrder);

        return salesOrderMapper.toDtoResponse(savedOrder);
    }



}
