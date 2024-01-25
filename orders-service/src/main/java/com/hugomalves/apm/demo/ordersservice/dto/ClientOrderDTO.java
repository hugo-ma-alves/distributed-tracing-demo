package com.hugomalves.apm.demo.ordersservice.dto;

import java.util.List;

public class ClientOrderDTO {
    private String clientName;
    private List<ProductOrderDTO> products;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<ProductOrderDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOrderDTO> products) {
        this.products = products;
    }
}