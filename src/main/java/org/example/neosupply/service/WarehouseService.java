package org.example.neosupply.service;

import org.example.neosupply.dto.request.WarehouseDTO;
import org.example.neosupply.dto.response.WarehouseDtoResponse;

import java.util.List;

public interface WarehouseService {


    public WarehouseDtoResponse createWarehouse(WarehouseDTO warehouseDTO);

    public WarehouseDtoResponse updateWarehouseById(Long id,WarehouseDTO warehouseDTO);

    public void deleteWarheouseById(Long id);

    public WarehouseDtoResponse findWarehouseById(Long id);

    public List<WarehouseDtoResponse> getAllWarehouses();
}
