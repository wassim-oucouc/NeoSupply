package org.example.neosupply.service.impl;


import jakarta.transaction.Transactional;
import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.entity.PurchaseOrder;
import org.example.neosupply.entity.SalesOrder;
import org.example.neosupply.entity.SalesOrderLine;
import org.example.neosupply.enumeration.SOStatus;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.mapper.SalesOrderMapper;
import org.example.neosupply.repository.SalesOrderRepository;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.PurchaseOrderService;
import org.example.neosupply.service.SalesOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final InventoryService inventoryService;
    private final SalesOrderMapper salesOrderMapper;
    private final PurchaseOrderService purchaseOrderService;

    public SalesOrderServiceImpl(SalesOrderRepository salesOrderRepository, InventoryService inventoryService, SalesOrderMapper salesOrderMapper, PurchaseOrderService purchaseOrderService)
    {
        this.salesOrderRepository = salesOrderRepository;
        this.inventoryService = inventoryService;
        this.salesOrderMapper = salesOrderMapper;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Transactional
    public SalesOrderDtoResponse createSalesOrder(SalesOrderDTO salesOrderDTO)
    {
       SalesOrder salesOrder =  this.salesOrderMapper.toEntity(salesOrderDTO);

        List<SalesOrderLine> salesOrderLines = salesOrder.getSalesOrderLines();

        for(SalesOrderLine salesOrderLine : salesOrderLines)
        {
            salesOrderLine.setSalesOrder(salesOrder);
            Integer quantity =   this.inventoryService.getProductQuantityByWarehouse(salesOrderLine.getProduct().getId(),salesOrder.getWarehouse().getId());
          InventoryDtoResponse inventoryDtoResponseFound =   this.inventoryService.findInventoryByProductIdAndWarehouseId(salesOrder.getWarehouse().getId(),salesOrderLine.getProduct().getId()).orElseThrow(() -> new InventoryNotFoudException("Inventory Not Found"));
            if(quantity < salesOrderLine.getQuantity())
            {
                InventoryDtoResponse inventoryDtoResponse = this.inventoryService.getInventoryByProductId(salesOrderLine.getProduct().getId());
                int remainingQty = salesOrderLine.getQuantity();

                     if(inventoryDtoResponse.getQuantityOnHand() >= remainingQty)
                     {
                        inventoryDtoResponse.setQuantityOnHand(inventoryDtoResponse.getQuantityOnHand() - salesOrderLine.getQuantity());
                         inventoryDtoResponseFound.setQuantityReserved(inventoryDtoResponseFound.getQuantityReserved() + inventoryDtoResponseFound.getQuantityReserved());
                         salesOrder.setStatus(SOStatus.RESERVED);
                         remainingQty = 0;
                     }
                     else
                     {
                         salesOrderLine.setQuantityToOrder(salesOrderLine.getQuantity() - inventoryDtoResponse.getQuantityOnHand());
                         salesOrder.setStatus(SOStatus.CREATED);

                }
            }

        }
       SalesOrder salesOrderCreated =  this.salesOrderRepository.save(salesOrder);
       return this.salesOrderMapper.toDtoResponse(salesOrderCreated);


    }
}
