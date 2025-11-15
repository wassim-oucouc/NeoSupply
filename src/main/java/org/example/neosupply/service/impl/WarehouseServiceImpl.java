package org.example.neosupply.service.impl;

import org.example.neosupply.dto.request.WarehouseDTO;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.exceptions.WarehouseNotFoundException;
import org.example.neosupply.mapper.WarehouseMapper;
import org.example.neosupply.repository.WarehouseRepository;
import org.example.neosupply.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    @Override
    public WarehouseDtoResponse createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDTO);
        warehouseRepository.save(warehouse);
        return warehouseMapper.toDtoResponse(warehouse);
    }

    @Override
    public WarehouseDtoResponse updateWarehouseById(Long id, WarehouseDTO warehouseDTO) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + id));

        warehouse.setCode(warehouseDTO.getCode());
        warehouse.setName(warehouseDTO.getName());
        warehouse.setLocation(warehouseDTO.getLocation());

        warehouseRepository.save(warehouse);
        return warehouseMapper.toDtoResponse(warehouse);
    }

    @Override
    public void deleteWarheouseById(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new WarehouseNotFoundException("Warehouse not found with id: " + id);
        }
        warehouseRepository.deleteById(id);
    }

    @Override
    public WarehouseDtoResponse findWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + id));
        return warehouseMapper.toDtoResponse(warehouse);
    }

    @Override
    public List<WarehouseDtoResponse> getAllWarehouses() {
        return warehouseRepository.findAll()
                .stream()
                .map(warehouseMapper::toDtoResponse)
                .toList();
    }
}
