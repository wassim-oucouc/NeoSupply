    package org.example.neosupply.mapper;

    import org.example.neosupply.dto.request.SalesOrderLineDTO;
    import org.example.neosupply.dto.response.SalesOrderLineDtoResponse;
    import org.example.neosupply.entity.Product;
    import org.example.neosupply.entity.SalesOrderLine;
    import org.hibernate.boot.model.relational.QualifiedName;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.Named;
    import org.mapstruct.Qualifier;

    import java.util.List;
    import java.util.stream.Collectors;

    @Mapper(componentModel = "spring")
    public abstract class SalesOrderLineMapper {


            @Mapping(target = "id", ignore = true)
            @Mapping(target = "product", source = "productId", qualifiedByName  = "mapProduct")
            @Mapping(target = "salesOrder", ignore = true)
            public abstract SalesOrderLine toEntity(SalesOrderLineDTO dto);

        @Mapping(source = "product", target = "productDtoResponse") // MapStruct will use ProductMapper
        @Mapping(source = "salesOrder", target = "salesOrderDtoResponse")
            public abstract SalesOrderLineDtoResponse toDtoResponse(SalesOrderLine entity);
        @Named("mapProduct")
            public Product mapProduct(Long productId) {
                if (productId == null) return null;
                Product product = new Product();
                product.setId(productId);
                return product;
        }

        public List<SalesOrderLine> toEntityList(List<SalesOrderLineDTO> dtos) {
            if (dtos == null) return null;
            return dtos.stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
        }
    }
