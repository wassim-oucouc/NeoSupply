package org.example.neosupply.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDtoResponse {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private double price;
    private boolean active;
}
