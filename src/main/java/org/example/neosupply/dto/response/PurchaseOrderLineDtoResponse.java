package org.example.neosupply.dto.response;



import lombok.Data;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PurchaseOrderLineDtoResponse{

    private Long id;
    private Long quantity;
    private BigDecimal unitPrice;
    private List<Product> product;
    private PurchaseOrder purchaseOrder;
}
