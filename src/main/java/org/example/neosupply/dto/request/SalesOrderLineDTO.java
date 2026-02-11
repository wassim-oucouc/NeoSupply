package org.example.neosupply.dto.request;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.SalesOrder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderLineDTO {

    private Long id;
    private int quantity;
    private double price;
    private int quantityToOrder;


    private Long productId;

    private Long salesOrderId;
}
