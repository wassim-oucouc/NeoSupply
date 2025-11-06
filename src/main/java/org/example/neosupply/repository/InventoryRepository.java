package org.example.neosupply.repository;

import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Integer findQuantityByProductIdAndWarehouse_Id(Long productId, Long warehouseId);

    List<Inventory> findInventoryByProduct_Id(Long productId);

    Inventory findInventoryByProduct_IdAndWarehouse_Id(Long productId, Long warehouseId);
}
