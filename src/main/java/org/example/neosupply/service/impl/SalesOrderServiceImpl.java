package org.example.neosupply.service.impl;


import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.entity.PurchaseOrder;
import org.example.neosupply.entity.SalesOrder;
import org.example.neosupply.entity.SalesOrderLine;
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

    public SalesOrderDtoResponse createSalesOrder(SalesOrderDTO salesOrderDTO)
    {
       SalesOrder salesOrder =  this.salesOrderMapper.toEntity(salesOrderDTO);

        List<SalesOrderLine> salesOrderLines = salesOrder.getSalesOrderLines();

        for(SalesOrderLine salesOrderLine : salesOrderLines)
        {
            Integer quantity =   this.inventoryService.getProductQuantityByWarehouse(salesOrderLine.getProduct().getId(),salesOrder.getWarehouse().getId());
          InventoryDtoResponse inventoryDtoResponseFound =   this.inventoryService.findInventoryByProductIdAndWarehouseId(salesOrder.getWarehouse().getId(),salesOrderLine.getProduct().getId());
            if(quantity < salesOrderLine.getQuantity())
            {
                List<InventoryDtoResponse> inventoryDtoResponseList = this.inventoryService.getInventoriesByProductId(salesOrderLine.getProduct().getId());
                for(InventoryDtoResponse inventoryDtoResponse : inventoryDtoResponseList)
                {
                     if(inventoryDtoResponse.getQuantityOnHand() >= salesOrderLine.getQuantity())
                     {
                        inventoryDtoResponse.setQuantityOnHand(inventoryDtoResponse.getQuantityOnHand() - salesOrderLine.getQuantity());
                         inventoryDtoResponseFound.setQuantityOnHand(salesOrderLine.getQuantity());
                     }
                     else
                     {
                         salesOrderLine.setQuantityToOrder(salesOrderLine.getQuantity() - inventoryDtoResponse.getQuantityOnHand());


                     }
                }
            }

        }
       SalesOrder salesOrderCreated =  this.salesOrderRepository.save(salesOrder);
       return this.salesOrderMapper.toDtoResponse(salesOrderCreated);


    }
}
