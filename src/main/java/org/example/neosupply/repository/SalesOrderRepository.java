package org.example.neosupply.repository;


import org.example.neosupply.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Long> {
    List<SalesOrder> getSalesOrdersByClient_Id(Long clientId);
}
