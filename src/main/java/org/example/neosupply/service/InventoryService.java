package org.example.neosupply.service;


import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InventoryService {

    public InventoryDtoResponse createInventory(InventoryDTO inventoryDTO);

    public InventoryDtoResponse updateInventoryById(InventoryDTO inventoryDTO, Long id);
    InventoryDtoResponse updateInventory(InventoryDTO inventoryDTO);

    public void deleteInventoryById(Long id);

    public InventoryDtoResponse findInventoryById(Long id);

    public List<InventoryDtoResponse> getAllProducts();

    public Integer getProductQuantityByWarehouse(Long productId,Long warehouseId);
    public InventoryDtoResponse getInventoryByProductId(Long productId);
    public Optional<InventoryDtoResponse> findInventoryByProductIdAndWarehouseId(Long warehouseId, Long productId);
    public void TransformFromQuantityReservedToQuantityHand(Long productId,Integer QuantityOnHand,Integer QuantityReserved);

}