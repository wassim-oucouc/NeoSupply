package org.example.neosupply.service.impl;

import jakarta.transaction.Transactional;
import org.example.neosupply.dto.request.ShipmentDTO;
import org.example.neosupply.dto.response.ShipmentDtoResponse;
import org.example.neosupply.entity.Shipment;
import org.example.neosupply.mapper.ShipmentMapper;
import org.example.neosupply.repository.ShipmentRepository;
import org.example.neosupply.service.ShipmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                               ShipmentMapper shipmentMapper) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
    }

    @Override
    public ShipmentDtoResponse createShipment(ShipmentDTO shipmentDTO) {
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        Shipment saved = shipmentRepository.save(shipment);
        return shipmentMapper.toDtoResponse(saved);
    }

    @Override
    public ShipmentDtoResponse updateShipment(Long id, ShipmentDTO shipmentDTO) {
        Shipment existing = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));

        existing.setTrackingNumber(shipmentDTO.getTrackingNumber());
        existing.setShipmentStatus(shipmentDTO.getShipmentStatus());
        existing.setCarrier(existing.getCarrier()); // on conserve le carrier existant
        Shipment updated = shipmentRepository.save(existing);
        return shipmentMapper.toDtoResponse(updated);
    }

    @Override
    public ShipmentDtoResponse getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        return shipmentMapper.toDtoResponse(shipment);
    }

    @Override
    public List<ShipmentDtoResponse> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .map(shipmentMapper::toDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        shipmentRepository.delete(shipment);
    }
}
