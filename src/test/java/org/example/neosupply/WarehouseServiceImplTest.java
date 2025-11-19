package org.example.neosupply;

import org.example.neosupply.dto.request.WarehouseDTO;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.exceptions.WarehouseNotFoundException;
import org.example.neosupply.mapper.WarehouseMapper;
import org.example.neosupply.repository.WarehouseRepository;
import org.example.neosupply.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WarehouseServiceImplTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private WarehouseMapper warehouseMapper;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //========================
    // Test createWarehouse
    //========================
    @Test
    void testCreateWarehouse_success() {
        WarehouseDTO dto = new WarehouseDTO();
        dto.setCode("WH01");
        dto.setName("Main Warehouse");
        dto.setLocation("City A");

        Warehouse entity = new Warehouse();
        WarehouseDtoResponse response = new WarehouseDtoResponse();

        when(warehouseMapper.toEntity(dto)).thenReturn(entity);
        when(warehouseMapper.toDtoResponse(entity)).thenReturn(response);

        WarehouseDtoResponse result = warehouseService.createWarehouse(dto);

        verify(warehouseRepository).save(entity);
        assertEquals(response, result);
    }

    //========================
    // Test updateWarehouseById
    //========================
    @Test
    void testUpdateWarehouseById_success() {
        Long id = 1L;

        WarehouseDTO dto = new WarehouseDTO();
        dto.setCode("WH02");
        dto.setName("Updated Warehouse");
        dto.setLocation("City B");

        Warehouse entity = new Warehouse();
        WarehouseDtoResponse response = new WarehouseDtoResponse();

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(entity));
        when(warehouseMapper.toDtoResponse(entity)).thenReturn(response);

        WarehouseDtoResponse result = warehouseService.updateWarehouseById(id, dto);

        verify(warehouseRepository).save(entity);
        assertEquals(dto.getCode(), entity.getCode());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getLocation(), entity.getLocation());
        assertEquals(response, result);
    }

    @Test
    void testUpdateWarehouseById_notFound() {
        Long id = 100L;
        WarehouseDTO dto = new WarehouseDTO();

        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class,
                () -> warehouseService.updateWarehouseById(id, dto));
    }

    //========================
    // Test deleteWarehouseById
    //========================
    @Test
    void testDeleteWarehouseById_success() {
        Long id = 1L;
        when(warehouseRepository.existsById(id)).thenReturn(true);

        warehouseService.deleteWarheouseById(id);

        verify(warehouseRepository).deleteById(id);
    }

    @Test
    void testDeleteWarehouseById_notFound() {
        Long id = 2L;
        when(warehouseRepository.existsById(id)).thenReturn(false);

        assertThrows(WarehouseNotFoundException.class,
                () -> warehouseService.deleteWarheouseById(id));
    }

    //========================
    // Test findWarehouseById
    //========================
    @Test
    void testFindWarehouseById_success() {
        Long id = 1L;
        Warehouse entity = new Warehouse();
        WarehouseDtoResponse response = new WarehouseDtoResponse();

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(entity));
        when(warehouseMapper.toDtoResponse(entity)).thenReturn(response);

        WarehouseDtoResponse result = warehouseService.findWarehouseById(id);

        assertEquals(response, result);
    }

    @Test
    void testFindWarehouseById_notFound() {
        Long id = 100L;
        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(WarehouseNotFoundException.class,
                () -> warehouseService.findWarehouseById(id));
    }

    //========================
    // Test getAllWarehouses
    //========================
    @Test
    void testGetAllWarehouses_success() {
        Warehouse entity1 = new Warehouse();
        Warehouse entity2 = new Warehouse();

        WarehouseDtoResponse response1 = new WarehouseDtoResponse();
        WarehouseDtoResponse response2 = new WarehouseDtoResponse();

        when(warehouseRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(warehouseMapper.toDtoResponse(entity1)).thenReturn(response1);
        when(warehouseMapper.toDtoResponse(entity2)).thenReturn(response2);

        List<WarehouseDtoResponse> result = warehouseService.getAllWarehouses();

        assertEquals(2, result.size());
        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));
    }
}
