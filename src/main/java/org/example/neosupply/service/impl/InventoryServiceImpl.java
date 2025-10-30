package org.example.neosupply.service.impl;

import org.example.neosupply.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl {

    private InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository)
    {
        this.inventoryRepository = inventoryRepository;
    }

}
