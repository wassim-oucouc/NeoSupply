package org.example.neosupply.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.MovementType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class InventoryMovement {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MovementType type;
    private int quantity;
    private LocalDateTime movementDate;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Warehouse warehouse;

}
