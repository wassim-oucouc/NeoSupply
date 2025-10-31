package org.example.neosupply.repository;

import org.example.neosupply.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse,Long> {
    Warehouse findWarehouseById(Long id);
}
