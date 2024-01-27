package com.hugomalves.apm.demo.ordersservice.dto;

public class ProductOrderDto extends ProductDto {

    private Integer quantity;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}