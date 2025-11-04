package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.InventoryMovementDTO;
import org.example.neosupply.dto.response.InventoryMovementDtoResponse;
import org.example.neosupply.entity.InventoryMovement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMovementMapper {

    InventoryMovement toEntity(InventoryMovementDTO inventoryMovementDTO);
    InventoryMovementDtoResponse toDtoResponse(InventoryMovement inventoryMovement);
}
