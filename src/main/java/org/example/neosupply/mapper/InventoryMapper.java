package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryDTO inventoryDTO);
    Invent

}
