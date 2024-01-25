package com.hugomalves.apm.demo.ordersservice.model;

import com.hugomalves.apm.demo.ordersservice.dto.ProductOrderDTO;

import java.util.List;

public class ShippingRequest {
    private final Long orderId;
    private final List<ProductOrderDTO> products;

    public ShippingRequest(Long orderId, List<ProductOrderDTO> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public List<ProductOrderDTO> getProducts() {
        return products;
    }
}