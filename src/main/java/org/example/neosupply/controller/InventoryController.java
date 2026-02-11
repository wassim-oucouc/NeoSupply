package org.example.neosupply.controller;

import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryDtoResponse> createInventory(
            @RequestBody InventoryDTO inventoryDTO) {
        InventoryDtoResponse created = inventoryService.createInventory(inventoryDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDtoResponse> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryDTO inventoryDTO) {
        InventoryDtoResponse updated = inventoryService.updateInventoryById(inventoryDTO, id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/update-by-product-warehouse")
    public ResponseEntity<InventoryDtoResponse> updateInventoryByProductAndWarehouse(
            @RequestBody InventoryDTO inventoryDTO) {
        InventoryDtoResponse updated = inventoryService.updateInventory(inventoryDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDtoResponse>> getAllInventories() {
        List<InventoryDtoResponse> inventories = inventoryService.getAllProducts();
        return ResponseEntity.ok(inventories);
    }


    @GetMapping("/{id}")
    public ResponseEntity<InventoryDtoResponse> getInventoryById(@PathVariable Long id) {
        InventoryDtoResponse inventory = inventoryService.findInventoryById(id);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDtoResponse> getInventoryByProductId(
            @PathVariable Long productId) {
        InventoryDtoResponse inventory = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/product/{productId}/warehouse/{warehouseId}")
    public ResponseEntity<InventoryDtoResponse> getInventoryByProductAndWarehouse(
            @PathVariable Long productId,
            @PathVariable Long warehouseId) {

        Optional<InventoryDtoResponse> inventory =
                inventoryService.findInventoryByProductIdAndWarehouseId(warehouseId, productId);

        return inventory
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> getProductQuantityByWarehouse(
            @RequestParam Long productId,
            @RequestParam Long warehouseId) {
        Integer quantity = inventoryService.getProductQuantityByWarehouse(productId, warehouseId);
        return ResponseEntity.ok(quantity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventoryById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transform-quantity")
    public ResponseEntity<Void> transformQuantity(
            @RequestParam Long productId,
            @RequestParam Integer quantityOnHand,
            @RequestParam Integer quantityReserved) {

        inventoryService.TransformFromQuantityReservedToQuantityHand(
                productId, quantityOnHand, quantityReserved);

        return ResponseEntity.ok().build();
    }
}
