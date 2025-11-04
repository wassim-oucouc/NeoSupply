package org.example.neosupply.controller;

import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductDtoResponse> createProduct(@RequestBody ProductDTO productDTO, @RequestBody InventoryDTO inventoryDTO)
    {
        ProductDtoResponse productDtoResponse =  this.productService.createProduct(productDTO);

        return ResponseEntity.ok().body(productDtoResponse);
    }

    @GetMapping("/product/details/{id}")
    public ResponseEntity<ProductDtoResponse> getProductById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok().body(this.productService.findProductById(id));
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id)
    {
        this.productService.deleteProductById(id);
        return ResponseEntity.ok().body("product is deleted with success");
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<ProductDtoResponse> updateProductById(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO)
    {
        return ResponseEntity.ok().body(this.productService.updateProductById(productDTO,id));
    }
}
