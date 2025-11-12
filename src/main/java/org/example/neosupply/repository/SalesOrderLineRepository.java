package org.example.neosupply.repository;


import org.example.neosupply.entity.SalesOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderLineRepository extends JpaRepository<SalesOrderLine,Long> {

    List<SalesOrderLine> getSalesOrderLineByProduct_Id(Long productId);
}
