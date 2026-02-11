    package org.example.neosupply.mapper;

    import org.example.neosupply.dto.request.SalesOrderLineDTO;
    import org.example.neosupply.dto.response.SalesOrderLineDtoResponse;
    import org.example.neosupply.entity.Product;
    import org.example.neosupply.entity.SalesOrderLine;
    import org.example.neosupply.repository.ProductRepository;
    import org.hibernate.boot.model.relational.QualifiedName;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.Named;
    import org.mapstruct.Qualifier;
    import org.springframework.beans.factory.annotation.Autowired;

    import java.util.List;
    import java.util.stream.Collectors;

    @Mapper(componentModel = "spring" , uses = {ProductMapper.class})
    public abstract class SalesOrderLineMapper {

        @Autowired
        protected ProductRepository productRepository;


            @Mapping(target = "id", ignore = true)
            @Mapping(target = "product", source = "productId", qualifiedByName  = "mapProduct")
            @Mapping(target = "salesOrder", ignore = true)
            public abstract SalesOrderLine toEntity(SalesOrderLineDTO dto);

        @Mapping(source = "product", target = "productDtoResponse")
            public abstract SalesOrderLineDtoResponse toDtoResponse(SalesOrderLine entity);
        @Named("mapProduct")
            public Product mapProduct(Long productId) {
                if (productId == null) return null;
            return productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with ID " + productId));

        }

        public List<SalesOrderLine> toEntityList(List<SalesOrderLineDTO> dtos) {
            if (dtos == null) return null;
            return dtos.stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
        }
    }
