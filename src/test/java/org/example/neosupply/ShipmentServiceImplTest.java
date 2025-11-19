package org.example.neosupply;

import org.example.neosupply.dto.request.ShipmentDTO;
import org.example.neosupply.dto.response.CarrierDtoResponse;
import org.example.neosupply.dto.response.ShipmentDtoResponse;
import org.example.neosupply.entity.Shipment;
import org.example.neosupply.enumeration.ShipmentStatus;
import org.example.neosupply.mapper.ShipmentMapper;
import org.example.neosupply.repository.ShipmentRepository;
import org.example.neosupply.service.impl.ShipmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShipmentServiceImplTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShipmentMapper shipmentMapper;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 🔹 Test : Création d'un shipment
    @Test
    void testCreateShipment() {
        ShipmentDTO dto = ShipmentDTO.builder()
                .trackingNumber("TRK123")
                .shipmentStatus(ShipmentStatus.PLANNED)
                .carrierId(1L)
                .build();

        Shipment shipment = new Shipment();
        Shipment savedShipment = new Shipment();

        CarrierDtoResponse carrierResponse = CarrierDtoResponse.builder()
                .id(1L)
                .name("DHL")
                .build();

        ShipmentDtoResponse response = ShipmentDtoResponse.builder()
                .id(1L)
                .shipmentStatus(ShipmentStatus.PLANNED)
                .trackingNumber("TRK123")
                .shippedAt(Instant.now())
                .deliveredAt(null)
                .carrierDtoResponse(carrierResponse)
                .build();

        when(shipmentMapper.toEntity(dto)).thenReturn(shipment);
        when(shipmentRepository.save(shipment)).thenReturn(savedShipment);
        when(shipmentMapper.toDtoResponse(savedShipment)).thenReturn(response);

        ShipmentDtoResponse result = shipmentService.createShipment(dto);

        verify(shipmentRepository).save(shipment);
        assertEquals(response, result);
    }

    @Test
    void testGetShipmentByIdFound() {
        Shipment shipment = new Shipment();

        CarrierDtoResponse carrierResponse = CarrierDtoResponse.builder()
                .id(2L)
                .name("FedEx")
                .build();

        ShipmentDtoResponse response = ShipmentDtoResponse.builder()
                .id(2L)
                .shipmentStatus(ShipmentStatus.DELIVERED)
                .trackingNumber("TRK456")
                .shippedAt(Instant.now())
                .deliveredAt(Instant.now())
                .carrierDtoResponse(carrierResponse)
                .build();

        when(shipmentRepository.findById(2L)).thenReturn(Optional.of(shipment));
        when(shipmentMapper.toDtoResponse(shipment)).thenReturn(response);

        ShipmentDtoResponse result = shipmentService.getShipmentById(2L);

        assertEquals(response, result);
    }

    @Test
    void testGetShipmentByIdNotFound() {
        when(shipmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> shipmentService.getShipmentById(1L));
    }

    @Test
    void testUpdateShipment() {
        ShipmentDTO dto = ShipmentDTO.builder()
                .trackingNumber("TRK999")
                .shipmentStatus(ShipmentStatus.DELIVERED)
                .carrierId(3L)
                .build();

        Shipment existing = new Shipment();
        Shipment updated = new Shipment();

        CarrierDtoResponse carrierResponse = CarrierDtoResponse.builder()
                .id(3L)
                .name("UPS")
                .build();

        ShipmentDtoResponse response = ShipmentDtoResponse.builder()
                .id(3L)
                .shipmentStatus(ShipmentStatus.DELIVERED)
                .trackingNumber("TRK999")
                .shippedAt(Instant.now())
                .deliveredAt(Instant.now())
                .carrierDtoResponse(carrierResponse)
                .build();

        when(shipmentRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(shipmentRepository.save(existing)).thenReturn(updated);
        when(shipmentMapper.toDtoResponse(updated)).thenReturn(response);

        ShipmentDtoResponse result = shipmentService.updateShipment(3L, dto);

        assertEquals(response, result);
        assertEquals("TRK999", existing.getTrackingNumber());
        assertEquals(ShipmentStatus.DELIVERED, existing.getShipmentStatus());
    }

    @Test
    void testDeleteShipmentFound() {
        Shipment shipment = new Shipment();
        when(shipmentRepository.findById(1L)).thenReturn(Optional.of(shipment));

        shipmentService.deleteShipment(1L);

        verify(shipmentRepository).delete(shipment);
    }

    @Test
    void testDeleteShipmentNotFound() {
        when(shipmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> shipmentService.deleteShipment(1L));
    }

    @Test
    void testGetAllShipments() {
        Shipment shipment1 = new Shipment();
        Shipment shipment2 = new Shipment();

        CarrierDtoResponse carrier1 = CarrierDtoResponse.builder()
                .id(1L)
                .name("DHL")
                .build();

        CarrierDtoResponse carrier2 = CarrierDtoResponse.builder()
                .id(2L)
                .name("FedEx")
                .build();

        ShipmentDtoResponse response1 = ShipmentDtoResponse.builder()
                .id(1L)
                .shipmentStatus(ShipmentStatus.PLANNED)
                .trackingNumber("TRK1")
                .shippedAt(Instant.now())
                .deliveredAt(null)
                .carrierDtoResponse(carrier1)
                .build();

        ShipmentDtoResponse response2 = ShipmentDtoResponse.builder()
                .id(2L)
                .shipmentStatus(ShipmentStatus.DELIVERED)
                .trackingNumber("TRK2")
                .shippedAt(Instant.now())
                .deliveredAt(Instant.now())
                .carrierDtoResponse(carrier2)
                .build();

        when(shipmentRepository.findAll()).thenReturn(List.of(shipment1, shipment2));
        when(shipmentMapper.toDtoResponse(shipment1)).thenReturn(response1);
        when(shipmentMapper.toDtoResponse(shipment2)).thenReturn(response2);

        List<ShipmentDtoResponse> result = shipmentService.getAllShipments();

        assertEquals(2, result.size());
        assertTrue(result.contains(response1));
        assertTrue(result.contains(response2));
    }
}
