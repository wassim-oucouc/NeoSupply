package org.example.neosupply.mapper;

import org.example.neosupply.dto.request.ShipmentDTO;
import org.example.neosupply.dto.response.ShipmentDtoResponse;
import org.example.neosupply.entity.Carrier;
import org.example.neosupply.entity.Shipment;
import org.example.neosupply.repository.CarrierRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ShipmentMapper {

    @Autowired
    protected CarrierRepository carrierRepository;

    @Mapping(source = "carrierId", target = "carrier")
    public abstract Shipment toEntity(ShipmentDTO dto);

    @Mapping(source = "carrier", target = "carrierDtoResponse")
    public abstract ShipmentDtoResponse toDtoResponse(Shipment shipment);

    protected Carrier mapCarrier(Long id) {
        if (id == null) return null;
        return carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));
    }
}
