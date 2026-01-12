package org.example.neosupply.controller;

import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.service.SalesOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<SalesOrderDtoResponse> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) {
        SalesOrderDtoResponse created = salesOrderService.createSalesOrder(salesOrderDTO);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    @PutMapping("purchaseorder/approve/{purchaseId}/{warehouseId}")
    public ResponseEntity<SalesOrderDtoResponse> approveSalesOrder(
            @PathVariable Long id,
            @RequestParam Long carrierId) {
        SalesOrderDtoResponse approved = salesOrderService.approveSalesOrder(id, carrierId);
        return ResponseEntity.ok(approved);
    }

}
