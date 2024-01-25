package com.hugomalves.apm.demo.ordersservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    private Long id;

    private String barcode;

    private String description;

    private BigDecimal price;

    public Product(Long id, String barcode, String description, BigDecimal price) {
        this.id = id;
        this.barcode = barcode;
        this.description = description;
        this.price = price;
    }

    public Product() {
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
