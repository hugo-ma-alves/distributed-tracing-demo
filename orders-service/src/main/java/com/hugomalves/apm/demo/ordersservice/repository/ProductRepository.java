package com.hugomalves.apm.demo.ordersservice.repository;

import com.hugomalves.apm.demo.ordersservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}