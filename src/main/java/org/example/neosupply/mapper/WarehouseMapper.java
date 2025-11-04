package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.WarehouseDTO;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    Warehouse toEntity(WarehouseDTO warehouseDTO);
    WarehouseDtoResponse toDtoResponse(Warehouse warehouse);
}
