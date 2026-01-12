package org.example.neosupply.dto.request;


import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.neosupply.entity.Carrier;
import org.example.neosupply.enumeration.ShipmentStatus;

import java.time.Instant;

@Builder
@Data
public class ShipmentDTO {

    private Long id;
    private ShipmentStatus shipmentStatus;
    private String trackingNumber;
    private Instant shippedAt;
    private Instant deliveredAt;

    private Long carrierId;
}
