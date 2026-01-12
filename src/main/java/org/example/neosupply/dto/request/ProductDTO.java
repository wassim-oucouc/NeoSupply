package org.example.neosupply.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private double price;
    private boolean active;
}
