package org.example.neosupply.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.SOStatus;

import java.time.Instant;

@Entity
@Getter
@Setter
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SOStatus status;
    private Instant createdAt;

    @ManyToOne
    private Clients client;
    @ManyToOne
    private Warehouse warehouse;




}
