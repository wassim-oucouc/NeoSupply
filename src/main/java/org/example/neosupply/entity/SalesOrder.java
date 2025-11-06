package org.example.neosupply.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.neosupply.enumeration.SOStatus;

import java.time.Instant;
import java.util.List;

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
    private Users client;
    @ManyToOne
    private Warehouse warehouse;

    @OneToMany
    private List<SalesOrderLine> salesOrderLines;




}
