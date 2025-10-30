package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper{

    ProductDtoResponse toDtoResponse(Product product);
    Product toEntity(ProductDTO productDTO);
}
