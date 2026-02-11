package org.example.neosupply.service.impl;

import jakarta.transaction.Transactional;
import org.example.neosupply.dto.request.*;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.entity.*;
import org.example.neosupply.enumeration.MovementType;
import org.example.neosupply.enumeration.SOStatus;
import org.example.neosupply.enumeration.ShipmentStatus;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.exceptions.SalesOrderCancellationException;
import org.example.neosupply.exceptions.SalesOrderNotFoundException;
import org.example.neosupply.mapper.SalesOrderMapper;
import org.example.neosupply.repository.*;
import org.example.neosupply.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ClientRepository clientRepository;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository,
                                 ClientRepository clientRepository,
                                 WarehouseRepository warehouseRepository,
                                 InventoryService inventoryService,
                                 ProductRepository productRepository,
                                 SalesOrderMapper salesOrderMapper,
                                 PurchaseOrderService purchaseOrderService, InventoryMovementService inventoryMovementService, ShipmentService shipmentService, CarrierService carrierService) {
        this.salesOrderRepository = salesOrderRepository;
        this.inventoryService = inventoryService;
        this.salesOrderMapper = salesOrderMapper;
        this.purchaseOrderService = purchaseOrderService;
        this.inventoryMovementService = inventoryMovementService;
        this.shipmentService = shipmentService;
        this.carrierService = carrierService;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public SalesOrderDtoResponse createSalesOrder(SalesOrderDTO salesOrderDTO) {

        SalesOrder salesOrder = salesOrderMapper.toEntity(salesOrderDTO);

        for (SalesOrderLine line : salesOrder.getSalesOrderLines()) {

            line.setSalesOrder(salesOrder);

            int warehouseId = Math.toIntExact(salesOrder.getWarehouse().getId());
            int productId   = Math.toIntExact(line.getProduct().getId());
            int orderQty    = line.getQuantity();   // primitive → never null

            // Inventory for product + warehouse
            InventoryDtoResponse inventory =
                    inventoryService
                            .findInventoryByProductIdAndWarehouseId((long) warehouseId, (long) productId)
                            .orElseThrow(() ->
                                    new InventoryNotFoudException(
                                            "Inventory not found for product " + productId +
                                                    " in warehouse " + warehouseId
                                    )
                            );

            int availableQty = inventory.getQuantityOnHand();   // primitive
            int reservedQty  = inventory.getQuantityReserved(); // primitive

            // Case 1: enough stock → reserve
            if (availableQty >= orderQty) {

                inventory.setQuantityOnHand(availableQty - orderQty);
                inventory.setQuantityReserved(reservedQty + orderQty);

                InventoryMovementDTO movement = InventoryMovementDTO.builder()
                        .movementDate(LocalDateTime.now())
                        .quantity(orderQty)
                        .productId((long) productId)
                        .warehouseId((long) warehouseId)
                        .type(MovementType.OUTBOUND)
                        .build();

                inventoryMovementService.createInventoryMovement(movement);

                line.setQuantityToOrder(0);
                salesOrder.setStatus(SOStatus.RESERVED);

            }
            // Case 2: partial stock
            else if (availableQty > 0) {

                inventory.setQuantityOnHand(0);
                inventory.setQuantityReserved(reservedQty + availableQty);

                line.setQuantityToOrder(orderQty - availableQty);
                salesOrder.setStatus(SOStatus.CREATED);
            }
            // Case 3: no stock at all
            else {
                line.setQuantityToOrder(orderQty);
                salesOrder.setStatus(SOStatus.CREATED);
            }
        }

        SalesOrder saved = salesOrderRepository.save(salesOrder);
        return salesOrderMapper.toDtoResponse(saved);
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

    public Page<SalesOrderDtoResponse> getSalesOrderAll(Pageable pageable)
    {
        return this.salesOrderRepository.findAll(pageable).map(salesOrderMapper::toDtoResponse);
    }

    public SalesOrderDtoResponse cancelSalesOrder(Long salesOrderId)
    {
        SalesOrder salesOrder = this.salesOrderRepository.findById(salesOrderId).orElseThrow(() -> new SalesOrderNotFoundException("sales order not exists with id : " + salesOrderId));

        if(salesOrder.getStatus().equals(SOStatus.SHIPPED) || salesOrder.getStatus().equals(SOStatus.DELIVERED))
        {
            throw new SalesOrderCancellationException("sales cannot be cancelled because its already shipped");
        }

        salesOrder.setStatus(SOStatus.CANCELED);
        SalesOrder salesOrderCreated = this.salesOrderRepository.save(salesOrder);
       return  this.salesOrderMapper.toDtoResponse(salesOrderCreated);
    }

    public List<SalesOrderDtoResponse> getSalesOrdersByClientId(Long clientId)
    {
        if(clientId == null)
        {
            throw new IllegalArgumentException("clientId is null");
        }

       return  this.salesOrderRepository.getSalesOrdersByClient_Id(clientId).stream().map(salesOrderMapper::toDtoResponse).toList();
    }



}
