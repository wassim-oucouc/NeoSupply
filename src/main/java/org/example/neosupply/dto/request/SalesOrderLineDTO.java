package org.example.neosupply.dto.request;

import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.SalesOrder;


@Data
@Builder
public class SalesOrderLineDTO {

    private Long id;
    private int quantity;
    private double price;
    private int quantityToOrder;


    private Long productId;

    private Long salesOrderId;
}
