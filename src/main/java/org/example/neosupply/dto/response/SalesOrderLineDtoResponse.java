package org.example.neosupply.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesOrderLineDtoResponse {
    private Long id;
    private int quantity;
    private double price;
    private int quantityToOrder;


    private ProductDtoResponse productDtoResponse;

}
