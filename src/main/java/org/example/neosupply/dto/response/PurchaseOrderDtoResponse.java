package org.example.neosupply.dto.response;

import lombok.Data;
import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.entity.Supplier;

import java.time.LocalDate;
import java.util.List;


@Data
public class PurchaseOrderDtoResponse {

    private Long id;
    private LocalDate orderDate;
    private String status;

    private SupplierDtoResponse supplierDtoResponse;
    private List<PurchaseOrderLineDtoResponse> purchaseOrderLineDtoResponseList;

}
