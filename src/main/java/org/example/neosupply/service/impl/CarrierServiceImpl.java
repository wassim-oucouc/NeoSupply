package org.example.neosupply.service.impl;

import jakarta.transaction.Transactional;
import org.example.neosupply.dto.request.CarrierDTO;
import org.example.neosupply.dto.response.CarrierDtoResponse;
import org.example.neosupply.entity.Carrier;
import org.example.neosupply.entity.Shipment;
import org.example.neosupply.mapper.CarrierMapper;
import org.example.neosupply.repository.CarrierRepository;
import org.example.neosupply.repository.ShipmentRepository;
import org.example.neosupply.service.CarrierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarrierServiceImpl implements CarrierService {

    private final CarrierRepository carrierRepository;
    private final ShipmentRepository shipmentRepository;
    private final CarrierMapper carrierMapper;

    public CarrierServiceImpl(CarrierRepository carrierRepository,
                              ShipmentRepository shipmentRepository,
                              CarrierMapper carrierMapper) {
        this.carrierRepository = carrierRepository;
        this.shipmentRepository = shipmentRepository;
        this.carrierMapper = carrierMapper;
    }

    @Override
    public CarrierDtoResponse createCarrier(CarrierDTO carrierDTO) {
        Carrier carrier = carrierMapper.toEntity(carrierDTO);
        carrier.setActive(carrierDTO.getActive() != null ? carrierDTO.getActive() : true);
        Carrier saved = carrierRepository.save(carrier);
        return carrierMapper.toDtoResponse(saved);
    }

    @Override
    public CarrierDtoResponse updateCarrier(Long id, CarrierDTO carrierDTO) {
        Carrier existing = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));

        existing.setName(carrierDTO.getName());
        existing.setActive(carrierDTO.getActive());

        Carrier updated = carrierRepository.save(existing);
        return carrierMapper.toDtoResponse(updated);
    }

    @Override
    public List<CarrierDtoResponse> getAllCarriers() {
        return carrierRepository.findAll().stream()
                .map(carrierMapper::toDtoResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CarrierDtoResponse getCarrierById(Long id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));
        return carrierMapper.toDtoResponse(carrier);
    }

    @Override
    public void deleteCarrier(Long id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));
        carrierRepository.delete(carrier);
    }

    @Override
    public CarrierDtoResponse activateCarrier(Long id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));
        carrier.setActive(true);
        Carrier updated = carrierRepository.save(carrier);
        return carrierMapper.toDtoResponse(updated);
    }

    @Override
    public CarrierDtoResponse deactivateCarrier(Long id) {
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));
        carrier.setActive(false);
        Carrier updated = carrierRepository.save(carrier);
        return carrierMapper.toDtoResponse(updated);
    }

    @Override
    public CarrierDtoResponse assignShipmentsToCarrier(Long carrierId, List<Long> shipmentIds) {
        Carrier carrier = carrierRepository.findById(carrierId)
                .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + carrierId));

        List<Shipment> shipmentsToAssign = shipmentIds.stream()
                .map(id -> shipmentRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id)))
                .collect(Collectors.toList());

        shipmentsToAssign.forEach(shipment -> shipment.setCarrier(carrier));

        if (carrier.getShipment() != null) {
            carrier.getShipment().addAll(shipmentsToAssign);
        } else {
            carrier.setShipment(shipmentsToAssign);
        }

        Carrier updatedCarrier = carrierRepository.save(carrier);
        return carrierMapper.toDtoResponse(updatedCarrier);
    }
}
