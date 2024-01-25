package com.hugomalves.apm.demo.ordersservice.repository;

import com.hugomalves.apm.demo.ordersservice.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}