package org.example.neosupply.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
public class PurchaseOrderDTO {


    private Long id;
    private LocalDate orderDate;
    private String status;

    private Long supplierId;

    private List<PurchaseOrderLineDTO> purchaseOrderLineDTOList;


}
