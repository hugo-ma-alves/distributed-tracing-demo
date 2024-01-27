package com.hugomalves.apm.demo.ordersservice.dto;

import java.math.BigDecimal;

public class ProductDto {
    private Long id;
    private String barcode;
    private String description;
    private BigDecimal price;

    public ProductDto(Long id, String barcode, String description, BigDecimal price) {
        this.id = id;
        this.barcode = barcode;
        this.description = description;
        this.price = price;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
