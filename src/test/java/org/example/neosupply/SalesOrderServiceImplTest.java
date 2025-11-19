package org.example.neosupply;

import org.example.neosupply.dto.request.InventoryMovementDTO;
import org.example.neosupply.dto.request.SalesOrderDTO;
import org.example.neosupply.dto.request.SalesOrderLineDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.InventoryMovementDtoResponse;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.dto.response.SalesOrderDtoResponse;
import org.example.neosupply.dto.response.WarehouseDtoResponse;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.SalesOrder;
import org.example.neosupply.entity.SalesOrderLine;
import org.example.neosupply.entity.Warehouse;
import org.example.neosupply.mapper.SalesOrderMapper;
import org.example.neosupply.repository.SalesOrderRepository;
import org.example.neosupply.service.*;
import org.example.neosupply.service.impl.SalesOrderServiceImpl;
import org.example.neosupply.enumeration.MovementType;
import org.example.neosupply.enumeration.SOStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SalesOrderServiceImplTest {

    @Mock
    private SalesOrderRepository salesOrderRepository;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private SalesOrderMapper salesOrderMapper;

    @Mock
    private PurchaseOrderService purchaseOrderService;

    @Mock
    private InventoryMovementService inventoryMovementService;

    @Mock
    private ShipmentService shipmentService;

    @Mock
    private CarrierService carrierService;

    @InjectMocks
    private SalesOrderServiceImpl salesOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSalesOrder() {
        // --- DTO de ligne de commande
        SalesOrderLineDTO lineDTO = SalesOrderLineDTO.builder()
                .productId(1L)
                .quantity(5)
                .build();

        // --- DTO de commande
        SalesOrderDTO dto = SalesOrderDTO.builder()
                .warehouseId(1L)
                .salesOrderLineDTOS(List.of(lineDTO))
                .build();

        // --- Entités
        Product product = new Product();
        product.setId(1L);

        Warehouse warehouse = new Warehouse();
        warehouse.setId(1L);

        SalesOrderLine lineEntity = new SalesOrderLine();
        lineEntity.setProduct(product);
        lineEntity.setQuantity(5);

        SalesOrder orderEntity = new SalesOrder();
        orderEntity.setWarehouse(warehouse);
        orderEntity.setSalesOrderLines(List.of(lineEntity));

        SalesOrder savedOrder = new SalesOrder();

        // --- DTO réponses
        ProductDtoResponse productDtoResponse = new ProductDtoResponse();
        productDtoResponse.setId(1L);
        productDtoResponse.setName("Produit Test");

        WarehouseDtoResponse warehouseDtoResponse = new WarehouseDtoResponse();
        warehouseDtoResponse.setId(1L);
        warehouseDtoResponse.setName("Warehouse Test");

        InventoryDtoResponse inventoryDtoResponse = new InventoryDtoResponse();
        inventoryDtoResponse.setId(1L);
        inventoryDtoResponse.setQuantityOnHand(10);
        inventoryDtoResponse.setQuantityReserved(0);
        inventoryDtoResponse.setProductDtoResponse(productDtoResponse);
        inventoryDtoResponse.setWarehouseDtoResponse(warehouseDtoResponse);

        SalesOrderDtoResponse response = SalesOrderDtoResponse.builder().build();

        InventoryMovementDtoResponse movementResponse = new InventoryMovementDtoResponse();
        movementResponse.setId(1L);
        movementResponse.setQuantity(lineDTO.getQuantity());
        movementResponse.setProductDtoResponse(productDtoResponse);
        movementResponse.setWarehouseDtoResponse(warehouseDtoResponse);
        movementResponse.setMovementDate(LocalDateTime.now());
        movementResponse.setType(MovementType.OUTBOUND);

        // --- Mocks
        when(salesOrderMapper.toEntity(dto)).thenReturn(orderEntity);
        when(inventoryService.getProductQuantityByWarehouse(1L, 1L)).thenReturn(0); // déclenche la logique OUTBOUND
        when(inventoryService.findInventoryByProductIdAndWarehouseId(1L, 1L))
                .thenReturn(Optional.of(inventoryDtoResponse));
        when(inventoryService.getInventoryByProductId(1L)).thenReturn(inventoryDtoResponse);
        when(salesOrderRepository.save(orderEntity)).thenReturn(savedOrder);
        when(salesOrderMapper.toDtoResponse(savedOrder)).thenReturn(response);

        // Remplacer doNothing() par thenReturn(...) car la méthode retourne InventoryMovementDtoResponse
        when(inventoryMovementService.createInventoryMovement(any(InventoryMovementDTO.class)))
                .thenReturn(movementResponse);

        // --- Call
        SalesOrderDtoResponse result = salesOrderService.createSalesOrder(dto);

        // --- Vérifications
        verify(inventoryMovementService, times(1))
                .createInventoryMovement(any(InventoryMovementDTO.class));
        verify(salesOrderRepository).save(orderEntity);
        assertEquals(response, result);
    }
}
