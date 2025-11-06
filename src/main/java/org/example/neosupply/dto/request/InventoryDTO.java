package org.example.neosupply.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.Warehouse;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDTO {

    private Long id;
    private int quantityOnHand;
    private int quantityReserved;
    private Long productId;
    private Long WarehouseId;
}
