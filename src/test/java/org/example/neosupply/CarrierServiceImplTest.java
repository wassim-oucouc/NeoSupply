package org.example.neosupply;

import org.example.neosupply.dto.request.CarrierDTO;
import org.example.neosupply.dto.response.CarrierDtoResponse;
import org.example.neosupply.entity.Carrier;
import org.example.neosupply.mapper.CarrierMapper;
import org.example.neosupply.repository.CarrierRepository;
import org.example.neosupply.repository.ShipmentRepository;
import org.example.neosupply.service.impl.CarrierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarrierServiceImplTest {

    @Mock
    private CarrierRepository carrierRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private CarrierMapper carrierMapper;

    @InjectMocks
    private CarrierServiceImpl carrierService;

    private Carrier carrier;
    private CarrierDTO carrierDTO;
    private CarrierDtoResponse carrierDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carrierDTO = CarrierDTO.builder()
                .id(1L)
                .name("Carrier 1")
                .active(true)
                .shipmentDTOList(List.of())
                .build();

        carrier = new Carrier();
        carrier.setId(1L);
        carrier.setName("Carrier 1");
        carrier.setActive(true);
        carrier.setShipment(List.of());

        carrierDtoResponse = CarrierDtoResponse.builder()
                .id(1L)
                .name("Carrier 1")
                .active(true)
                .shipmentDtoResponseList(List.of())
                .build();
    }

    @Test
    void testCreateCarrier() {
        when(carrierMapper.toEntity(carrierDTO)).thenReturn(carrier);
        when(carrierRepository.save(carrier)).thenReturn(carrier);
        when(carrierMapper.toDtoResponse(carrier)).thenReturn(carrierDtoResponse);

        CarrierDtoResponse result = carrierService.createCarrier(carrierDTO);

        assertNotNull(result);
        assertEquals("Carrier 1", result.getName());
        verify(carrierRepository, times(1)).save(carrier);
    }

    @Test
    void testUpdateCarrier() {
        when(carrierRepository.findById(1L)).thenReturn(Optional.of(carrier));
        when(carrierRepository.save(carrier)).thenReturn(carrier);
        when(carrierMapper.toDtoResponse(carrier)).thenReturn(carrierDtoResponse);

        CarrierDtoResponse result = carrierService.updateCarrier(1L, carrierDTO);

        assertEquals(true, result.getActive());
        verify(carrierRepository).save(carrier);
    }

    @Test
    void testGetAllCarriers() {
        when(carrierRepository.findAll()).thenReturn(List.of(carrier));
        when(carrierMapper.toDtoResponse(carrier)).thenReturn(carrierDtoResponse);

        List<CarrierDtoResponse> result = carrierService.getAllCarriers();

        assertEquals(1, result.size());
        assertEquals("Carrier 1", result.get(0).getName());
    }

    @Test
    void testGetCarrierById() {
        when(carrierRepository.findById(1L)).thenReturn(Optional.of(carrier));
        when(carrierMapper.toDtoResponse(carrier)).thenReturn(carrierDtoResponse);

        CarrierDtoResponse result = carrierService.getCarrierById(1L);

        assertEquals("Carrier 1", result.getName());
    }

    @Test
    void testDeleteCarrier() {
        when(carrierRepository.findById(1L)).thenReturn(Optional.of(carrier));

        carrierService.deleteCarrier(1L);

        verify(carrierRepository).delete(carrier);
    }

    @Test
    void testDeactivateCarrier() {
        when(carrierRepository.findById(1L)).thenReturn(Optional.of(carrier));

        carrier.setActive(false);
        when(carrierRepository.save(carrier)).thenReturn(carrier);

        CarrierDtoResponse deactivatedDto = CarrierDtoResponse.builder()
                .id(1L)
                .name("Carrier 1")
                .active(false)
                .shipmentDtoResponseList(List.of())
                .build();

        when(carrierMapper.toDtoResponse(carrier)).thenReturn(deactivatedDto);

        CarrierDtoResponse result = carrierService.deactivateCarrier(1L);

        assertFalse(result.getActive());
    }

}
