package org.example.neosupply.dto.response;

import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.neosupply.entity.Clients;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.enumeration.SOStatus;

import java.time.Instant;


@Data
@Builder
public class SalesOrderDtoResponse {

    private Long id;
    private SOStatus status;
    private Instant createdAt;


    private UserDtoResponse clientDtoResponse;
    private WarehouseDtoResponse warehouseDtoResponse;
}
