package org.example.neosupply.mapper;

import org.example.neosupply.dto.request.CarrierDTO;
import org.example.neosupply.dto.response.CarrierDtoResponse;
import org.example.neosupply.dto.request.ShipmentDTO;
import org.example.neosupply.dto.response.ShipmentDtoResponse;
import org.example.neosupply.entity.Carrier;
import org.example.neosupply.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ShipmentMapper.class})
public abstract class CarrierMapper {

    @Autowired
    protected ShipmentMapper shipmentMapper;

    @Mapping(target = "shipment", source = "shipmentDTOList")
    public abstract Carrier toEntity(CarrierDTO dto);

    @Mapping(target = "shipmentDtoResponseList", source = "shipment")
    public abstract CarrierDtoResponse toDtoResponse(Carrier entity);

    protected List<Shipment> mapShipmentDTOList(List<ShipmentDTO> shipmentDTOList) {
        if (shipmentDTOList == null) return null;
        return shipmentDTOList.stream()
                .map(shipmentMapper::toEntity)
                .collect(Collectors.toList());
    }

    protected List<ShipmentDtoResponse> mapShipmentList(List<Shipment> shipmentList) {
        if (shipmentList == null) return null;
        return shipmentList.stream()
                .map(shipmentMapper::toDtoResponse)
                .collect(Collectors.toList());
    }
}
