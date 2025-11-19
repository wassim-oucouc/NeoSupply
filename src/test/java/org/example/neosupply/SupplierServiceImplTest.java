package org.example.neosupply;

import org.example.neosupply.dto.request.SupplierDTO;
import org.example.neosupply.dto.response.SupplierDtoResponse;
import org.example.neosupply.entity.Supplier;
import org.example.neosupply.exceptions.SupplierNotFoundException;
import org.example.neosupply.mapper.SupplierMapper;
import org.example.neosupply.repository.SupplierRepository;
import org.example.neosupply.service.impl.SupplierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSupplier() {
        SupplierDTO dto = new SupplierDTO();
        Supplier supplier = new Supplier();
        SupplierDtoResponse response = new SupplierDtoResponse();

        when(supplierMapper.toEntity(dto)).thenReturn(supplier);
        when(supplierMapper.toDtoResponse(supplier)).thenReturn(response);

        SupplierDtoResponse result = supplierService.createSupplier(dto);

        verify(supplierRepository, times(1)).save(supplier);
        assertEquals(response, result);
    }

    @Test
    void testFindSupplierByIdFound() {
        Supplier supplier = new Supplier();
        SupplierDtoResponse response = new SupplierDtoResponse();

        when(supplierRepository.findSupplierById(1L)).thenReturn(Optional.of(supplier));
        when(supplierMapper.toDtoResponse(supplier)).thenReturn(response);

        SupplierDtoResponse result = supplierService.findSupplierById(1L);

        assertEquals(response, result);
    }

    @Test
    void testFindSupplierByIdNotFound() {
        when(supplierRepository.findSupplierById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> supplierService.findSupplierById(1L));
    }

    @Test
    void testUpdateSupplier() {
        SupplierDTO dto = new SupplierDTO();
        Supplier supplierEntity = new Supplier();
        Supplier supplierFound = new Supplier();
        SupplierDtoResponse response = new SupplierDtoResponse();

        when(supplierRepository.findSupplierById(1L)).thenReturn(Optional.of(supplierFound));
        when(supplierMapper.toEntity(dto)).thenReturn(supplierEntity);
        when(supplierMapper.toDtoResponse(supplierFound)).thenReturn(response);

        SupplierDtoResponse result = supplierService.updateSupplier(1L, dto);

        assertEquals(response, result);
    }

    @Test
    void testDeleteSupplierById() {
        Supplier supplier = new Supplier();
        when(supplierRepository.findSupplierById(1L)).thenReturn(Optional.of(supplier));

        supplierService.deleteSupplierById(1L);

        verify(supplierRepository, times(1)).delete(supplier);
    }

    @Test
    void testDeleteSupplierByIdNotFound() {
        when(supplierRepository.findSupplierById(1L)).thenReturn(Optional.empty());

        assertThrows(SupplierNotFoundException.class, () -> supplierService.deleteSupplierById(1L));
    }

    @Test
    void testGetAllSuppliers() {
        Supplier supplier1 = new Supplier();
        Supplier supplier2 = new Supplier();
        SupplierDtoResponse response1 = new SupplierDtoResponse();
        SupplierDtoResponse response2 = new SupplierDtoResponse();

        when(supplierRepository.findAll()).thenReturn(List.of(supplier1, supplier2));
        when(supplierMapper.toDtoResponse(supplier1)).thenReturn(response1);
        when(supplierMapper.toDtoResponse(supplier2)).thenReturn(response2);

        List<SupplierDtoResponse> result = supplierService.getAllSuppliers();

        assertEquals(2, result.size());
        assertTrue(result.contains(response1));
        assertTrue(result.contains(response2));
    }
}
