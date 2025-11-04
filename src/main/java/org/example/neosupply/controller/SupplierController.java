package org.example.neosupply.controller;


import org.apache.coyote.Response;
import org.example.neosupply.dto.request.SupplierDTO;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.example.neosupply.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    public SupplierService supplierService;

    public SupplierController(SupplierService supplierService)
    {
        this.supplierService = supplierService;
    }

    @GetMapping("/supplier/all")
    public ResponseEntity<List<SupplierDtoResponse>> getAllSuppliers()
    {
        return ResponseEntity.ok().body(this.supplierService.getAllSuppliers());
    }

    @GetMapping("/supplier/details/{id}")
    public ResponseEntity<SupplierDtoResponse> getSupplierById(@PathVariable("id") Long id)
    {
        return ResponseEntity.ok().body(this.supplierService.findSupplierById(id));
    }


    @DeleteMapping("/supplier/delete/{id}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable("id") Long id)
    {
        this.supplierService.deleteSupplierById(id);
        return ResponseEntity.ok().body("supplier is deleted");
    }

    @PutMapping("/supplier/update/{id}")
    public ResponseEntity<SupplierDtoResponse> updateSupplierById(@PathVariable("id") Long id, @RequestBody SupplierDTO supplierDTO)
    {
       return ResponseEntity.ok().body(this.supplierService.updateSupplier(id,supplierDTO));
    }

    @PostMapping("/supplier/add")
    public ResponseEntity<SupplierDtoResponse> createSupplier(@RequestBody SupplierDTO supplierDTO)
    {
        return ResponseEntity.ok().body(this.supplierService.createSupplier(supplierDTO));
    }




}
