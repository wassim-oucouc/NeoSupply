package org.example.neosupply.dto.request;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.PurchaseOrder;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderLineDTO {

    private Long id;
    private Long quantity;
    private BigDecimal unitPrice;
    private int quantityToOrder;

    private Long productId;
    private Long purchaseId;
}
