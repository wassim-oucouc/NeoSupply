package org.example.neosupply.repository;

import org.example.neosupply.dto.response.InventoryDtoResponse;
import org.example.neosupply.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    @Query("SELECT i.quantityOnHand FROM Inventory i WHERE i.product.id = :productId AND i.warehouse.id = :warehouseId")
    Integer findQuantityByProductIdAndWarehouse_Id(Long productId, Long warehouseId);
    Optional<Inventory> findInventoryByProduct_Id(Long productId);

    Inventory findInventoryByProduct_IdAndWarehouse_Id(Long productId, Long warehouseId);
    Optional<Inventory> findInventoryByProductIdAndWarehouseId(Long productId, Long warehouseId);


    @Modifying
    @Query("update Inventory i set i.quantityOnHand = :quantityOnHand,i.quantityReserved = :quantityReserved where i.product.id = :productId")
    void updateQuantityOnHandAndQuantityReservedOrderByProduct_Id(int quantityOnHand, int quantityReserved,Long productId);
}
