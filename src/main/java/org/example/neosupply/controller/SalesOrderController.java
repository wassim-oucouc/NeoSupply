package org.example.neosupply.controller;

import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.service.SalesOrderService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SalesOrderDtoResponse> createSalesOrder(@RequestBody SalesOrderDTO salesOrderDTO) {
        SalesOrderDtoResponse created = salesOrderService.createSalesOrder(salesOrderDTO);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<SalesOrderDtoResponse> approveSalesOrder(
            @PathVariable Long id,
            @RequestParam Long carrierId) {
        SalesOrderDtoResponse approved = salesOrderService.approveSalesOrder(id, carrierId);
        return ResponseEntity.ok(approved);
    }

}
