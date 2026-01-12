package org.example.neosupply;

import org.example.neosupply.dto.request.InventoryMovementDTO;
import org.example.neosupply.dto.response.InventoryMovementDtoResponse;
import org.example.neosupply.entity.InventoryMovement;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.enumeration.MovementType;
import org.example.neosupply.exceptions.InventoryMovementNotFoundException;
import org.example.neosupply.mapper.InventoryMovementMapper;
import org.example.neosupply.repository.InventoryMovementRepository;
import org.example.neosupply.service.impl.InventoryMovementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryMovementServiceImplTest {

    @Mock
    private InventoryMovementRepository inventoryMovementRepository;

    @Mock
    private InventoryMovementMapper inventoryMovementMapper;

    @InjectMocks
    private InventoryMovementServiceImpl inventoryMovementService;

    private InventoryMovementDTO inventoryMovementDTO;
    private InventoryMovement inventoryMovement;
    private InventoryMovementDtoResponse inventoryMovementDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Product product = new Product();
        product.setId(1L);
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);

        inventoryMovementDTO = InventoryMovementDTO.builder()
                .id(1L)
                .type(MovementType.INBOUND)
                .quantity(50)
                .movementDate(LocalDateTime.now())
                .productId(product.getId())
                .warehouseId(warehouse.getId())
                .build();

        inventoryMovement = new InventoryMovement();
        inventoryMovement.setId(1L);
        inventoryMovement.setType(MovementType.INBOUND);
        inventoryMovement.setQuantity(50);
        inventoryMovement.setMovementDate(LocalDateTime.now());
        inventoryMovement.setProduct(product);
        inventoryMovement.setWarehouse(warehouse);

        inventoryMovementDtoResponse = new InventoryMovementDtoResponse();
        inventoryMovementDtoResponse.setId(1L);
    }

    @Test
    void testCreateInventoryMovement() {
        when(inventoryMovementMapper.toEntity(any())).thenReturn(inventoryMovement);
        when(inventoryMovementMapper.toDtoResponse(any())).thenReturn(inventoryMovementDtoResponse);
        when(inventoryMovementRepository.save(any())).thenReturn(inventoryMovement);

        InventoryMovementDtoResponse response = inventoryMovementService.createInventoryMovement(inventoryMovementDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(inventoryMovementRepository, times(1)).save(inventoryMovement);
    }

    @Test
    void testUpdateInventoryMovement_success() {
        when(inventoryMovementMapper.toEntity(any())).thenReturn(inventoryMovement);
        when(inventoryMovementRepository.findInventoryMovementById(1L)).thenReturn(Optional.of(inventoryMovement));
        when(inventoryMovementRepository.save(any())).thenReturn(inventoryMovement);
        when(inventoryMovementMapper.toDtoResponse(any())).thenReturn(inventoryMovementDtoResponse);

        InventoryMovementDtoResponse response = inventoryMovementService.updateInventoryMovement(inventoryMovementDTO, 1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(inventoryMovementRepository, times(1)).save(inventoryMovement);
    }

    @Test
    void testUpdateInventoryMovement_notFound() {
        when(inventoryMovementRepository.findInventoryMovementById(1L)).thenReturn(Optional.empty());

        assertThrows(InventoryMovementNotFoundException.class,
                () -> inventoryMovementService.updateInventoryMovement(inventoryMovementDTO, 1L));
    }

    @Test
    void testFindInventoryMovementById_success() {
        when(inventoryMovementRepository.findInventoryMovementById(1L)).thenReturn(Optional.of(inventoryMovement));
        when(inventoryMovementMapper.toDtoResponse(any())).thenReturn(inventoryMovementDtoResponse);

        InventoryMovementDtoResponse response = inventoryMovementService.findInventoryMovementById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testFindInventoryMovementById_notFound() {
        when(inventoryMovementRepository.findInventoryMovementById(1L)).thenReturn(Optional.empty());

        assertThrows(InventoryMovementNotFoundException.class,
                () -> inventoryMovementService.findInventoryMovementById(1L));
    }
}
