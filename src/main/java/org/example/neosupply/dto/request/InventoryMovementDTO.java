package org.example.neosupply.dto.request;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.enumeration.MovementType;

import java.time.LocalDateTime;


@Data
public class InventoryMovementDTO {

    private Long id;
    private MovementType type;
    private int quantity;
    private LocalDateTime movementDate;
    private Long productId ;
    private Long warehouseId;
}
