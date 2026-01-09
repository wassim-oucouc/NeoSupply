package org.example.neosupply.controller;


import org.apache.coyote.Response;
import org.example.neosupply.dto.request.SupplierDTO;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.example.neosupply.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    public SupplierService supplierService;

    public SupplierController(SupplierService supplierService)
    {
        this.supplierService = supplierService;
    }

    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    @GetMapping
    public ResponseEntity<List<SupplierDtoResponse>> getAllSuppliers()
    {
        return ResponseEntity.ok().body(this.supplierService.getAllSuppliers());
    }

    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDtoResponse> getSupplierById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok().body(this.supplierService.findSupplierById(id));
    }


    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable("id") Long id)
    {
        this.supplierService.deleteSupplierById(id);
        return ResponseEntity.ok().body("supplier is deleted");
    }
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDtoResponse> updateSupplierById(@PathVariable("id") Long id, @RequestBody SupplierDTO supplierDTO)
    {
       return ResponseEntity.ok().body(this.supplierService.updateSupplier(id,supplierDTO));
    }
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    @PostMapping
    public ResponseEntity<SupplierDtoResponse> createSupplier(@RequestBody SupplierDTO supplierDTO)
    {
        return ResponseEntity.ok().body(this.supplierService.createSupplier(supplierDTO));
    }




}
