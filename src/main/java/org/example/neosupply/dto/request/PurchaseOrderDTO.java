package org.example.neosupply.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderDTO {


    private Long id;
    private LocalDate orderDate;
    private String status;
    private Long supplierId;

    private List<PurchaseOrderLineDTO> purchaseOrderLineDTOList;


}
