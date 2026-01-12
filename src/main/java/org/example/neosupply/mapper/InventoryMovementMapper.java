package org.example.neosupply.mapper;

import org.example.neosupply.dto.request.InventoryMovementDTO;
import org.example.neosupply.dto.response.InventoryMovementDtoResponse;
import org.example.neosupply.entity.InventoryMovement;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.repository.WarehouseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class InventoryMovementMapper {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected WarehouseRepository warehouseRepository;

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "warehouseId", target = "warehouse")
    public abstract InventoryMovement toEntity(InventoryMovementDTO dto);

    public abstract InventoryMovementDtoResponse toDtoResponse(InventoryMovement movement);

    protected Product mapProduct(Long productId) {
        if (productId == null) return null;
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    protected Warehouse mapWarehouse(Long warehouseId) {
        if (warehouseId == null) return null;
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new InventoryNotFoudException("Warehouse not found"));
    }
}
