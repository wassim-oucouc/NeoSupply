package org.example.neosupply.repository;


import org.example.neosupply.entity.PurchaseOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderLineRepository extends JpaRepository<PurchaseOrderLine,Long> {
}
