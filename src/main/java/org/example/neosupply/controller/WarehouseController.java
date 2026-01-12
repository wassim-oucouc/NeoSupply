package org.example.neosupply.controller;


import org.example.neosupply.dto.request.WarehouseDTO;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {


    public final WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService)
    {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public WarehouseDtoResponse createWarehouse(@RequestBody WarehouseDTO warehouseDTO)
    {
       return this.warehouseService.createWarehouse(warehouseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public WarehouseDtoResponse getWarehouseById(@PathVariable("id") Long id)
    {
        return this.warehouseService.findWarehouseById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<?> deleteWarehouseById(@PathVariable("id") Long id)
    {
        this.warehouseService.deleteWarheouseById(id);
        return ResponseEntity.ok().body("warehouse is deleted");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<WarehouseDtoResponse> updateWarehouseById(@PathVariable("id") Long id,@RequestBody WarehouseDTO warehouseDTO)
    {
        return ResponseEntity.ok().body(this.warehouseService.updateWarehouseById(id,warehouseDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<WarehouseDtoResponse>> getAllWarehouses()
    {
        return ResponseEntity.ok().body(this.warehouseService.getAllWarehouses());
    }
}
