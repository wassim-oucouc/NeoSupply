package org.example.neosupply.controller;

import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Product;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final InventoryService inventoryService;

    @Autowired
    public ProductController(ProductService productService, InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<ProductDtoResponse> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDtoResponse productDtoResponse = productService.createProduct(productDTO);
        return ResponseEntity.ok(productDtoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoResponse> getProductById(@PathVariable Long id) {
        ProductDtoResponse productDtoResponse = productService.findProductById(id);
        return ResponseEntity.ok(productDtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDtoResponse> updateProductById(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDtoResponse updatedProduct = productService.updateProductById(productDTO, id);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @PatchMapping("/{sku}/deactivate")
    public ResponseEntity<Product> deactivateProductBySku(@PathVariable String sku) {
        Product product = productService.deactivateProduct(sku);
        return ResponseEntity.ok(product);
    }
}
