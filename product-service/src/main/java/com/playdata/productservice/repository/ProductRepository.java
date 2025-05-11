package com.playdata.productservice.repository;

import com.playdata.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.title LIKE %:query% OR p.description LIKE %:query% OR p.category LIKE %:query%")
    List<Product> searchProducts(@Param("query") String query);
}