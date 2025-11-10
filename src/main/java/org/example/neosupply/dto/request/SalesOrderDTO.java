package org.example.neosupply.dto.request;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.neosupply.entity.Clients;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.enumeration.SOStatus;

import java.time.Instant;
import java.util.List;

@Data
public class SalesOrderDTO {

    private Long id;
    private SOStatus status;
    private Instant createdAt;

    private Long clientId;
    private Long warehouseId;

    private List<SalesOrderLineDTO> salesOrderLineDTOS;

}
