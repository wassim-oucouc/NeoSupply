package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.PurchaseOrder;
import org.example.neosupply.entity.PurchaseOrderLine;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.exceptions.PurchaseOrderNotFoundException;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.repository.PurchaseOrderRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PurchaseOrderLineMapper {


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Mapping(source = "purchaseId", target = "purchaseOrder", qualifiedByName = "mapPurchaseOrder")
    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProduct")
    public abstract  PurchaseOrderLine toEntity(PurchaseOrderLineDTO purchaseOrderLineDTO);

    @Named("mapPurchaseOrder")
    public PurchaseOrder mapPurchaseOrder(Long purchaseId) {
        if (purchaseId == null) {
            return null;
        }
        return purchaseOrderRepository.findById(purchaseId)
                .orElseThrow(() -> new PurchaseOrderNotFoundException(
                        "PurchaseOrder not found with id: " + purchaseId));
    }

    @Named("mapProduct")
    public Product mapProduct(Long productId) {
        if (productId == null) return null;
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with id: " + productId));
    }
}
