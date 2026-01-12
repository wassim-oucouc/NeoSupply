package org.example.neosupply.repository;

import org.example.neosupply.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findProductById(Long id);

    @Query("SELECT p.id from Product p  where p.sku = :sku")
    Long getProductIdBySku(@RequestParam("sku") String sku);

    Long id(Long id);

    @Modifying
    @Query("UPDATE Product p SET p.active = false WHERE p.id = :id")
    Product deactivateById(@Param("id") Long id);
}
