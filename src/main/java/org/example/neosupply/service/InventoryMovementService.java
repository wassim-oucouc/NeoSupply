package org.example.neosupply.service;


import org.example.neosupply.dto.request.InventoryMovementDTO;
import org.example.neosupply.dto.response.InventoryMovementDtoResponse;
import org.springframework.stereotype.Service;

@Service
public interface InventoryMovementService {

    public InventoryMovementDtoResponse createInventoryMovement(InventoryMovementDTO inventoryMovementDTO);
    public InventoryMovementDtoResponse updateInventoryMovement(InventoryMovementDTO inventoryMovementDTO,Long id);
    public InventoryMovementDtoResponse findInventoryMovementById(Long id);

}
