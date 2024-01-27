package com.hugomalves.apm.demo.ordersservice.model;

import com.hugomalves.apm.demo.ordersservice.dto.ProductOrderDto;

import java.util.List;

public record ShippingRequest(Long orderId, List<ProductOrderDto> products) {
}