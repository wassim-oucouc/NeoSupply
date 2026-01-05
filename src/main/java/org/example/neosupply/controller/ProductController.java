package org.example.neosupply.controller;

import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Product;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
public class ProductController {

    private final ProductService productService;
    private final InventoryService inventoryService;

    @Autowired
    public ProductController(ProductService productService, InventoryService inventoryService)
    {
        this.productService = productService;
        this.inventoryService = inventoryService;

    }

    @PostMapping("/product/create")
    public ResponseEntity<ProductDtoResponse> createProduct(@RequestBody ProductDTO productDTO)
    {
        ProductDtoResponse productDtoResponse =  this.productService.createProduct(productDTO);

        return ResponseEntity.ok().body(productDtoResponse);
    }

    @GetMapping("/product/details/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ProductDtoResponse> getProductById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok().body(this.productService.findProductById(id));
    }

    @DeleteMapping("/product/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id)
    {
        this.productService.deleteProductById(id);
        return ResponseEntity.ok().body("product is deleted with success");
    }

    @PutMapping("/product/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ProductDtoResponse> updateProductById(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO)
    {
        return ResponseEntity.ok().body(this.productService.updateProductById(productDTO,id));
    }


    @PatchMapping("/api/products/{sku}/desactive")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Product> desactiveProductBySku(@PathVariable("sku") String sku)
    {
        return ResponseEntity.ok().body(this.productService.deactivateProduct(sku));
    }
}
