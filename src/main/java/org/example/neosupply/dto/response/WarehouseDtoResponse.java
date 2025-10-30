package org.example.neosupply.dto.response;


import lombok.Data;

@Data
public class WarehouseDtoResponse {
    private Long id;
    private String code;
    private String name;
    private String location;
}
