package org.example.neosupply.repository;

import org.example.neosupply.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    Optional<Warehouse> findWarehouseById(Long id);
}
