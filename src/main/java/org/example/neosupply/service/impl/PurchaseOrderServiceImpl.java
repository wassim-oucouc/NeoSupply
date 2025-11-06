package org.example.neosupply.service.impl;

import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.request.InventoryMovementDTO;
import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.dto.response.PurchaseOrderDtoResponse;
import org.example.neosupply.entity.*;
import org.example.neosupply.enumeration.POStatus;
import org.example.neosupply.exceptions.PurchaseOrderNotFoundException;
import org.example.neosupply.exceptions.QuantityNotEqualZeroException;
import org.example.neosupply.mapper.InventoryMapper;
import org.example.neosupply.mapper.PurchaseOrderLineMapper;
import org.example.neosupply.mapper.PurchaseOrderMapper;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.repository.PurchaseOrderLineRepository;
import org.example.neosupply.repository.PurchaseOrderRepository;
import org.example.neosupply.repository.WarehouseRepository;
import org.example.neosupply.service.InventoryMovementService;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.PurchaseOrderService;
import org.example.neosupply.service.SalesOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.neosupply.enumeration.MovementType.INBOUND;

public class PurchaseOrderServiceImpl implements PurchaseOrderService {


    private PurchaseOrderRepository purchaseOrderRepository;
    private PurchaseOrderLineRepository purchaseOrderLineRepository;
    private ProductRepository productRepository;
    private final PurchaseOrderLineMapper purchaseOrderLineMapper;
    private PurchaseOrderMapper purchaseOrderMapper;
    private InventoryService inventoryService;
    private WarehouseRepository warehouseRepository;
    private InventoryMapper inventoryMapper;
    private InventoryMovement inventoryMovement;
    private InventoryMovementService inventoryMovementService;


    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository purchaseOrderRepository,
            PurchaseOrderLineRepository purchaseOrderLineRepository,
            ProductRepository productRepository,
            PurchaseOrderLineMapper purchaseOrderLineMapper,
            PurchaseOrderMapper purchaseOrderMapper,
            InventoryService inventoryService,
            WarehouseRepository warehouseRepository,
            InventoryMapper inventoryMapper,
            InventoryMovementService inventoryMovementService
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderLineRepository = purchaseOrderLineRepository;
        this.productRepository = productRepository;
        this.purchaseOrderLineMapper = purchaseOrderLineMapper;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.inventoryService = inventoryService;
        this.warehouseRepository = warehouseRepository;
        this.inventoryMapper = inventoryMapper;
        this.inventoryMovementService = inventoryMovementService;
    }

    public PurchaseOrderDtoResponse receivePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO)
    {
        List<PurchaseOrderLine> purchaseOrderLines = new ArrayList<>();
        List<PurchaseOrderLineDTO> orderLines = purchaseOrderDTO.getPurchaseOrderLineDTOList();
        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(purchaseOrderDTO);
        purchaseOrder.setStatus(POStatus.RECEIVED);

        for(PurchaseOrderLineDTO purchasesLine : orderLines)
        {
            if(purchasesLine.getQuantity() < 0)
            {
                throw new QuantityNotEqualZeroException("Quantity must be great than zero");
            }

            PurchaseOrderLine purchaseOrderLine = purchaseOrderLineMapper.toEntity(purchasesLine);
            purchaseOrderLine.setPurchaseOrder(purchaseOrder);


            purchaseOrderLines.add(purchaseOrderLine);

        }

        purchaseOrder.setLines(purchaseOrderLines);
        purchaseOrderRepository.save(purchaseOrder);

       return  purchaseOrderMapper.toDtoResponse(purchaseOrder);

    }

    public PurchaseOrderDtoResponse approvePurchaseOrder(Long id,Long warehouseId)
    {
      PurchaseOrder purchaseOrder =   this.purchaseOrderRepository.findById(id).orElseThrow(() -> new PurchaseOrderNotFoundException("purchase order not found id : " + id));
      purchaseOrder.setStatus(POStatus.APPROVED);

      List<PurchaseOrderLine> purchaseOrderLines = purchaseOrder.getLines();

      for(PurchaseOrderLine purchasesLine : purchaseOrderLines)
      {
              InventoryDTO inventoryDTO = InventoryDTO.builder().productId(purchasesLine.getProduct().getId()).WarehouseId(warehouseId).quantityReserved(Math.toIntExact(purchasesLine.getQuantity())).build();
              InventoryMovementDTO inventoryMovementDTO = InventoryMovementDTO.builder().movementDate(LocalDateTime.now()).type(INBOUND).warehouseId(warehouseId).productId(purchasesLine.getProduct().getId()).quantity(Math.toIntExact(purchasesLine.getQuantity())).build();
              this.inventoryService.createInventory(inventoryDTO);
              this.inventoryMovementService.createInventoryMovement(inventoryMovementDTO);
      }

      this.purchaseOrderRepository.save(purchaseOrder);

    return  this.purchaseOrderMapper.toDtoResponse(purchaseOrder);
    }

    public List<PurchaseOrderDtoResponse> getAllPurchaseOrders()
    {
        return this.purchaseOrderRepository.findAll().stream().map(purchaseOrderMapper::toDtoResponse).toList();
    }
    public PurchaseOrderDtoResponse findPurchaseOrderById(Long id)
    {
        PurchaseOrder purchaseOrder = this.purchaseOrderRepository.findById(id).orElseThrow(() -> new PurchaseOrderNotFoundException("purchase order not found id : " + id));

       return  purchaseOrderMapper.toDtoResponse(purchaseOrder);
    }

}
