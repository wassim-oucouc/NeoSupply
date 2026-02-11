package org.example.neosupply.controller;

import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.response.PurchaseOrderDtoResponse;
import org.example.neosupply.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderDtoResponse> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrderDtoResponse response = purchaseOrderService.receivePurchaseOrder(purchaseOrderDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{purchaseId}/cancel")
    public ResponseEntity<PurchaseOrderDtoResponse> cancelPurchaseOrder(@PathVariable Long purchaseId) {
        PurchaseOrderDtoResponse response = purchaseOrderService.cancelPurchaseOrder(purchaseId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{purchaseId}/approve/{warehouseId}")
    public ResponseEntity<PurchaseOrderDtoResponse> approvePurchaseOrder(
            @PathVariable Long purchaseId,
            @PathVariable Long warehouseId) {
        PurchaseOrderDtoResponse response = purchaseOrderService.approvePurchaseOrder(purchaseId, warehouseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderDtoResponse>> getAllPurchaseOrders()
    {
        return ResponseEntity.ok().body(this.purchaseOrderService.getAllPurchaseOrders());
    }
}
