package org.example.neosupply.controller;

import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.response.PurchaseOrderDtoResponse;
import org.example.neosupply.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PurchaseOrderController {


    private final PurchaseOrderService purchaseOrderService;


    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }



    @PostMapping("/purchaseorder/create")
    public ResponseEntity<PurchaseOrderDtoResponse> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO)
    {
        return ResponseEntity.ok().body(this.purchaseOrderService.receivePurchaseOrder(purchaseOrderDTO));
    }


    @PutMapping("/purchaseorder/cancel/{purchaseId}")
    public ResponseEntity<PurchaseOrderDtoResponse> cancelPurchaseOrder(@PathVariable("purchaseId") Long purchaseId)
    {
        return ResponseEntity.ok().body(this.purchaseOrderService.cancelPurchaseOrder(purchaseId));
    }


    @PutMapping("purchaseorder/approve/{purchaseId}/{warehouseId}")
    public ResponseEntity<PurchaseOrderDtoResponse> approvePurchaseOrder(@PathVariable("purchaseId") Long purchaseId,@PathVariable("warehouseId") Long warehouseId)
    {
        return ResponseEntity.ok().body(this.purchaseOrderService.approvePurchaseOrder(purchaseId,warehouseId));
    }

}
