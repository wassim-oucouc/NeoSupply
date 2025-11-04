package org.example.neosupply.dto.request;


import lombok.Data;

@Data
public class WarehouseDTO {

    private Long id;
    private String code;
    private String name;
    private String location;
}
