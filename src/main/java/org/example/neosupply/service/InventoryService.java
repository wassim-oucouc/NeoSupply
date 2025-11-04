package org.example.neosupply.service;


import org.example.neosupply.dto.request.InventoryDTO;
import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    public InventoryDtoResponse createInventory(InventoryDTO inventoryDTO);

    public InventoryDtoResponse updateInventoryById(InventoryDTO inventoryDTO, Long id);

    public void deleteInventoryById(Long id);

    public InventoryDtoResponse findInventoryById(Long id);

    public List<InventoryDtoResponse> getAllProducts();
}