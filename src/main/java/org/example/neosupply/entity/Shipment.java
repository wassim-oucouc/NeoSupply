package org.example.neosupply.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.ShipmentStatus;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Shipment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ShipmentStatus shipmentStatus;
    private String trackingNumber;
    private Instant shippedAt;
    private Instant deliveredAt;

    @ManyToOne
    private Carrier carrier;

}
