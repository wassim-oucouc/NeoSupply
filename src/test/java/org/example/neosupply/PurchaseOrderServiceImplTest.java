package org.example.neosupply;

import org.example.neosupply.dto.request.PurchaseOrderLineDTO;
import org.example.neosupply.dto.request.PurchaseOrderDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.dto.response.PurchaseOrderDtoResponse;
import org.example.neosupply.entity.Product;
import org.example.neosupply.entity.PurchaseOrder;
import org.example.neosupply.entity.PurchaseOrderLine;
import org.example.neosupply.mapper.PurchaseOrderLineMapper;
import org.example.neosupply.mapper.PurchaseOrderMapper;
import org.example.neosupply.repository.PurchaseOrderLineRepository;
import org.example.neosupply.repository.PurchaseOrderRepository;
import org.example.neosupply.service.InventoryMovementService;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.impl.PurchaseOrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PurchaseOrderServiceImplTest {

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private PurchaseOrderLineRepository purchaseOrderLineRepository;

    @Mock
    private PurchaseOrderLineMapper purchaseOrderLineMapper;

    @Mock
    private PurchaseOrderMapper purchaseOrderMapper;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private InventoryMovementService inventoryMovementService;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    private PurchaseOrderLine line;
    private PurchaseOrderDtoResponse purchaseOrderDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setSku("SKU123");
        product.setPrice(100.0);
        product.setActive(true);

        line = new PurchaseOrderLine();
        line.setId(1L);
        line.setProduct(product);
        line.setQuantity(10L);

        purchaseOrderDtoResponse = new PurchaseOrderDtoResponse();
        purchaseOrderDtoResponse.setId(1L);
    }



    @Test
    void testApprovePurchaseOrder_newInventory() {
        PurchaseOrder po = new PurchaseOrder();
        po.setId(1L);
        po.setLines(List.of(line));

        when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(po));
        when(inventoryService.findInventoryByProductIdAndWarehouseId(anyLong(), anyLong()))
                .thenReturn(Optional.empty());
        when(purchaseOrderMapper.toDtoResponse(any())).thenReturn(purchaseOrderDtoResponse);

        PurchaseOrderDtoResponse response = purchaseOrderService.approvePurchaseOrder(1L, 1L);

        assertNotNull(response);
        verify(inventoryService, times(1)).createInventory(any());
        verify(inventoryMovementService, times(1)).createInventoryMovement(any());
    }

    @Test
    void testCancelPurchaseOrder() {
        PurchaseOrder po = new PurchaseOrder();
        po.setId(1L);
        po.setLines(List.of(line));

        when(purchaseOrderRepository.findById(1L)).thenReturn(Optional.of(po));
        when(inventoryService.getInventoryByProductId(anyLong())).thenReturn(new InventoryDtoResponse());
        when(purchaseOrderMapper.toDtoResponse(any())).thenReturn(purchaseOrderDtoResponse);

        PurchaseOrderDtoResponse response = purchaseOrderService.cancelPurchaseOrder(1L);

        assertNotNull(response);
        assertEquals(purchaseOrderDtoResponse.getId(), response.getId());
        verify(purchaseOrderRepository, times(1)).save(any());
    }
}
