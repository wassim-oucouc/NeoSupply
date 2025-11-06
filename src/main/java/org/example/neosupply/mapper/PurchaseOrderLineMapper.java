package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.entity.PurchaseOrder;
import org.example.neosupply.entity.PurchaseOrderLine;
import org.example.neosupply.exceptions.PurchaseOrderNotFoundException;
import org.example.neosupply.repository.PurchaseOrderRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PurchaseOrderLineMapper {


    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    @Mapping(source = "purchaseId", target = "purchaseOrder", qualifiedByName = "mapPurchaseOrder")
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
}
