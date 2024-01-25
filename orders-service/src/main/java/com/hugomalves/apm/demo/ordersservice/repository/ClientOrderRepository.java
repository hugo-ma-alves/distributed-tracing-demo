package com.hugomalves.apm.demo.ordersservice.repository;

import com.hugomalves.apm.demo.ordersservice.model.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
}