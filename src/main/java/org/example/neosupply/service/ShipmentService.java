package org.example.neosupply.service;

import org.example.neosupply.dto.request.ShipmentDTO;
import org.example.neosupply.dto.response.ShipmentDtoResponse;

import java.util.List;

public interface ShipmentService {

    ShipmentDtoResponse createShipment(ShipmentDTO shipmentDTO);

    ShipmentDtoResponse updateShipment(Long id, ShipmentDTO shipmentDTO);

    ShipmentDtoResponse getShipmentById(Long id);

    List<ShipmentDtoResponse> getAllShipments();

    void deleteShipment(Long id);
}
