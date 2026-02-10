package org.example.neosupply.mapper;


import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.response.PurchaseOrderDtoResponse;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.PurchaseOrder;
import org.example.neosupply.entity.Supplier;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.exceptions.SupplierNotFoundException;
import org.example.neosupply.repository.SupplierRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",uses = {PurchaseOrderLineMapper.class})
public abstract class PurchaseOrderMapper {

    @Autowired
    protected SupplierRepository supplierRepository;
    @Autowired
    protected SupplierMapper supplierMapper;


    @Mapping(source = "supplierId", target = "supplier",qualifiedByName = "mapSupplier")
    public abstract PurchaseOrder toEntity(PurchaseOrderDTO purchaseOrderDTO);
    @Mapping(source = "supplier", target = "supplierDtoResponse",qualifiedByName = "mapSupplierDtoResponse")
    @Mapping(source  = "lines" , target  = "purchaseOrderLineDtoResponseList")
    public abstract PurchaseOrderDtoResponse toDtoResponse(PurchaseOrder purchaseOrder);


    @Named("mapSupplier")
    protected Supplier mapSupplier(Long id) {
        if (id == null) return null;
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("supplier not found"));
    }
    @Named("mapSupplierDtoResponse")
    protected SupplierDtoResponse mapSupplierDtoResponse(Supplier supplier)
    {

        if (supplier == null) return null;
       return  supplierMapper.toDtoResponse(supplier);
    }
}
