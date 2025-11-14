package org.example.neosupply;

import org.example.neosupply.dto.request.ProductDTO;
import org.example.neosupply.dto.response.ProductDtoResponse;
import org.example.neosupply.entity.Inventory;
import org.example.neosupply.entity.Product;
import org.example.neosupply.exceptions.ProductNotFoundException;
import org.example.neosupply.mapper.ProductMapper;
import org.example.neosupply.repository.ProductRepository;
import org.example.neosupply.service.InventoryService;
import org.example.neosupply.service.ProductService;
import org.example.neosupply.service.SalesOrderLineService;
import org.example.neosupply.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class
ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private SalesOrderLineService salesOrderLineService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductDTO dto = new ProductDTO();
        Product product = new Product();
        ProductDtoResponse response = new ProductDtoResponse();

        when(productMapper.toEntity(dto)).thenReturn(product);
        when(productMapper.toDtoResponse(product)).thenReturn(response);

        ProductDtoResponse result = productService.createProduct(dto);

        verify(productRepository).save(product);
        assertEquals(response, result);
    }

    @Test
    void testUpdateProductById_ProductExists() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Test Product");
        dto.setSku("SKU123");
        dto.setPrice(10.0);
        dto.setActive(true);
        dto.setDescription("Desc");

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        ProductDtoResponse response = new ProductDtoResponse();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productMapper.toDtoResponse(existingProduct)).thenReturn(response);

        ProductDtoResponse result = productService.updateProductById(dto, 1L);

        verify(productRepository).save(existingProduct);
        assertEquals("Test Product", existingProduct.getName());
        assertEquals(response, result);
    }

    @Test
    void testUpdateProductById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        ProductDTO dto = new ProductDTO();
        assertThrows(ProductNotFoundException.class, () -> productService.updateProductById(dto, 1L));
    }

    @Test
    void testDeleteProductById() {
        productService.deleteProductById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void testFindProductById_ProductExists() {
        Product product = new Product();
        ProductDtoResponse response = new ProductDtoResponse();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDtoResponse(product)).thenReturn(response);

        ProductDtoResponse result = productService.findProductById(1L);
        assertEquals(response, result);
    }

    @Test
    void testFindProductById_ProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(1L));
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        ProductDtoResponse dto1 = new ProductDtoResponse();
        ProductDtoResponse dto2 = new ProductDtoResponse();

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(productMapper.toDtoResponse(product1)).thenReturn(dto1);
        when(productMapper.toDtoResponse(product2)).thenReturn(dto2);

        List<ProductDtoResponse> result = productService.getAllProducts();
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }


    @Test
    void testDeactivateProduct_Success() {
        Long productId = 1L;
        String sku = "SKU123";
        Product product = new Product();

        when(productRepository.getProductIdBySku(sku)).thenReturn(productId);
        when(salesOrderLineService.getSalesOrderLineByProductId(productId)).thenReturn(List.of());
        when(productRepository.findProductById(productId)).thenReturn(product);

        Product result = productService.deactivateProduct(sku);

        verify(productRepository).deactivateById(productId);
        assertEquals(product, result);
    }
}
