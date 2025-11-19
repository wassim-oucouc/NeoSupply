package org.example.neosupply.dto.response;

import lombok.Builder;
import lombok.Data;


@Data

public class InventoryDtoResponse {

    private Long id;
    private int quantityOnHand;
    private int quantityReserved;
    private ProductDtoResponse productDtoResponse;
    private WarehouseDtoResponse warehouseDtoResponse;
}
