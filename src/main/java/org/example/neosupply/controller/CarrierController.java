package org.example.neosupply.controller;

import org.example.neosupply.dto.request.CarrierDTO;
import org.example.neosupply.dto.response.CarrierDtoResponse;
import org.example.neosupply.service.CarrierService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carriers")
public class CarrierController {

    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @PostMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<CarrierDtoResponse> createCarrier(@RequestBody CarrierDTO carrierDTO) {
        CarrierDtoResponse created = carrierService.createCarrier(carrierDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<CarrierDtoResponse> updateCarrier(
            @PathVariable Long id,
            @RequestBody CarrierDTO carrierDTO) {
        CarrierDtoResponse updated = carrierService.updateCarrier(id, carrierDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<List<CarrierDtoResponse>> getAllCarriers() {
        List<CarrierDtoResponse> carriers = carrierService.getAllCarriers();
        return ResponseEntity.ok(carriers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<CarrierDtoResponse> getCarrierById(@PathVariable Long id) {
        CarrierDtoResponse carrier = carrierService.getCarrierById(id);
        return ResponseEntity.ok(carrier);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long id) {
        carrierService.deleteCarrier(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<CarrierDtoResponse> activateCarrier(@PathVariable Long id) {
        CarrierDtoResponse carrier = carrierService.activateCarrier(id);
        return ResponseEntity.ok(carrier);
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<CarrierDtoResponse> deactivateCarrier(@PathVariable Long id) {
        CarrierDtoResponse carrier = carrierService.deactivateCarrier(id);
        return ResponseEntity.ok(carrier);
    }

    @PostMapping("/{id}/assign-shipments")
    @PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
    public ResponseEntity<CarrierDtoResponse> assignShipments(
            @PathVariable Long id,
            @RequestBody List<Long> shipmentIds) {
        CarrierDtoResponse updatedCarrier = carrierService.assignShipmentsToCarrier(id, shipmentIds);
        return ResponseEntity.ok(updatedCarrier);
    }
}
