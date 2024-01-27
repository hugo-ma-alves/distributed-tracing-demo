package com.hugomalves.apm.demo.ordersservice.dto;

import java.util.List;

public class OrderDto extends ClientOrderRequest {

    private Long id;

    public OrderDto(Long id, String clientName, List<ProductOrderDto> products) {
        super(clientName, products);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}