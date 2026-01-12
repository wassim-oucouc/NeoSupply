package org.example.neosupply.dto.request;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
public class PurchaseOrderLineDTO {

    private Long id;
    private Long quantity;
    private BigDecimal unitPrice;
    private int quantityToOrder;

    private Long productId;
    private Long purchaseId;
}
