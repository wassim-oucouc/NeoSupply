package org.example.neosupply.dto.response;


import lombok.Builder;
import lombok.Data;
import org.example.neosupply.dto.request.ShipmentDTO;

import java.util.List;

@Builder
@Data
public class CarrierDtoResponse {

    private Long id;
    private String name;
    private Boolean active;
    private List<ShipmentDtoResponse> shipmentDtoResponseList;
}
