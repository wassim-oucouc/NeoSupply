package org.example.neosupply.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.neosupply.enumeration.SOStatus;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderDTO {

    private Long id;
    private SOStatus status;
    private Instant createdAt;

    private Long clientId;
    private Long warehouseId;

    private List<SalesOrderLineDTO> salesOrderLineDTOS;

}
