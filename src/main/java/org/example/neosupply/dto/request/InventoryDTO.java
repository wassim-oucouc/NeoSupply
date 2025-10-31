package org.example.neosupply.dto.request;

import lombok.Data;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.Warehouse;


@Data
public class InventoryDTO {

    private Long id;
    private int quantityOnHand;
    private int quantityReserved;
    private Long productId;
    private Long WarehouseId;
}
