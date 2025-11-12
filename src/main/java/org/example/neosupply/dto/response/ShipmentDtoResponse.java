package org.example.neosupply.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.neosupply.entity.Carrier;
import org.example.neosupply.enumeration.ShipmentStatus;

import java.time.Instant;


@Data
@Builder
public class ShipmentDtoResponse {

    private Long id;
    private ShipmentStatus shipmentStatus;
    private String trackingNumber;
    private Instant shippedAt;
    private Instant deliveredAt;

    private CarrierDtoResponse carrierDtoResponse;
}
