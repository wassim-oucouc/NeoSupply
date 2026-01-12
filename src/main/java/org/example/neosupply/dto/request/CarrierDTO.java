package org.example.neosupply.dto.request;

import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.example.neosupply.entity.Shipment;

import java.util.List;


@Data
@Builder
public class CarrierDTO {

    private Long id;
    private String name;
    private Boolean active;
    private List<ShipmentDTO> shipmentDTOList;
}
