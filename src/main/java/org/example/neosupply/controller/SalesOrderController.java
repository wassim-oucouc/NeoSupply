package org.example.neosupply.controller;

import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.entity.SalesOrder;
import org.example.neosupply.service.SalesOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    @PutMapping("purchaseorder/approve/{purchaseId}/{warehouseId}")
    public ResponseEntity<SalesOrderDtoResponse> approveSalesOrder(
            @PathVariable Long id,
            @RequestParam Long carrierId) {
        SalesOrderDtoResponse approved = salesOrderService.approveSalesOrder(id, carrierId);
        return ResponseEntity.ok(approved);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SalesOrder>> getSalesOrderAllPage(
            @RequestParam Long size,
            @RequestParam Long page) {
        Pageable pageable = PageRequest.of(Math.toIntExact(size), Math.toIntExact(page));

        return ResponseEntity.ok().body(this.salesOrderService.getSalesOrderAll(pageable));
    }



}
