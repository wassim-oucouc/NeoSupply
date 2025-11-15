package org.example.neosupply.dto.response;

import lombok.Data;
import org.example.neosupply.enumeration.MovementType;

import java.time.LocalDateTime;

@Data
public class InventoryMovementDtoResponse {

    private Long id;
    private MovementType type;
    private int quantity;
    private LocalDateTime movementDate;
    private ProductDtoResponse productDtoResponse ;
    private WarehouseDtoResponse warehouseDtoResponse;
}
