package org.example.neosupply.repository;


import org.example.neosupply.entity.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement,Long> {
    Optional<InventoryMovement> findInventoryMovementById(Long id);
}
