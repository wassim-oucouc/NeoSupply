package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.SupplierDTO;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.example.neosupply.entity.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDtoResponse toDtoResponse(Supplier supplier);
    Supplier toEntity(SupplierDTO supplierDTO);
}
