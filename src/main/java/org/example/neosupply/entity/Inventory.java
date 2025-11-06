package org.example.neosupply.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private int quantityOnHand;
    private int quantityReserved;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Warehouse warehouse;
}
