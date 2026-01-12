package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.entity.Inventory;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.exceptions.InventoryNotFoudException;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.repository.WarehouseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class InventoryMapper {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected WarehouseRepository warehouseRepository;

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "warehouseId", target = "warehouse")
    public abstract Inventory toEntity(InventoryDTO dto);
    @Mapping(source = "product", target = "productDtoResponse")
    @Mapping(source = "warehouse", target = "warehouseDtoResponse")
    public abstract InventoryDtoResponse toDtoResponse(Inventory inventory);

    protected Product mapProduct(Long id) {
        if (id == null) return null;
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    protected Warehouse mapWarehouse(Long id) {
        if (id == null) return null;
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoudException("Warehouse not found"));
    }

}
