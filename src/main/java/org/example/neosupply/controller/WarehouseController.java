package org.example.neosupply.controller;


import org.example.neosupply.dto.request.WarehouseDTO;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WarehouseController {


    public final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService)
    {
        this.warehouseService = warehouseService;
    }

    @PostMapping("/warehouse/create")
    public WarehouseDtoResponse createWarehouse(@RequestBody WarehouseDTO warehouseDTO)
    {
       return this.warehouseService.createWarehouse(warehouseDTO);
    }

    @GetMapping("/warehouse/details/{id}")
    public WarehouseDtoResponse getWarehouseById(@PathVariable("id") Long id)
    {
        return this.warehouseService.findWarehouseById(id);
    }

    @DeleteMapping("/warehouse/delete/{id}")
    public ResponseEntity<?> deleteWarehouseById(@PathVariable("id") Long id)
    {
        this.warehouseService.deleteWarheouseById(id);
        return ResponseEntity.ok().body("warehouse is deleted");
    }

    @PutMapping("/warehouse/update/{id}")
    public ResponseEntity<WarehouseDtoResponse> updateWarehouseById(@PathVariable("id") Long id,@RequestBody WarehouseDTO warehouseDTO)
    {
        return ResponseEntity.ok().body(this.warehouseService.updateWarehouseById(id,warehouseDTO));
    }

    @GetMapping("/warehouse/all")
    public ResponseEntity<List<WarehouseDtoResponse>> getAllWarehouses()
    {
        return ResponseEntity.ok().body(this.warehouseService.getAllWarehouses());
    }
}
