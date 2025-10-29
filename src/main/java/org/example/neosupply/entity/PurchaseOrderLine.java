package org.example.neosupply.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class PurchaseOrderLine{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantity;
    private BigDecimal unitPrice;

    @OneToMany
    private List<Product> product;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

}
