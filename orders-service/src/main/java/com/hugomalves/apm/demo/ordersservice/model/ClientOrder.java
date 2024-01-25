package com.hugomalves.apm.demo.ordersservice.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String client;

    @OneToMany(mappedBy = "clientOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderProduct> orderProducts = new HashSet<>();

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        orderProducts.remove(orderProduct);
        orderProduct.setOrder(null);
    }

    public Long getId() {
        return id;
    }

    public String getClient() {
        return client;
    }

    public Set<OrderProduct> getOrderProducts() {
        return Set.copyOf(orderProducts);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
