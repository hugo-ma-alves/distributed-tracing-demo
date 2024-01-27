package com.hugomalves.apm.demo.ordersservice.dto;

import java.util.List;

public class ClientOrderRequest {
    private String clientName;
    private List<ProductOrderDto> products;

    public ClientOrderRequest(String clientName, List<ProductOrderDto> products) {
        this.clientName = clientName;
        this.products = products;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<ProductOrderDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderDto> products) {
        this.products = products;
    }
}