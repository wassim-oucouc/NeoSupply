package org.example.neosupply.service;

import org.example.neosupply.dto.request.CarrierDTO;
import org.example.neosupply.dto.response.CarrierDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarrierService {

    CarrierDtoResponse createCarrier(CarrierDTO carrierDTO);

    CarrierDtoResponse updateCarrier(Long id, CarrierDTO carrierDTO);

    List<CarrierDtoResponse> getAllCarriers();

    CarrierDtoResponse getCarrierById(Long id);

    void deleteCarrier(Long id);

    CarrierDtoResponse activateCarrier(Long id);

    CarrierDtoResponse deactivateCarrier(Long id);

    // Assign shipments to carrier
    CarrierDtoResponse assignShipmentsToCarrier(Long carrierId, List<Long> shipmentIds);
}
