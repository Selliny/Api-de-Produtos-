package com.selliny.productapi.controller;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRecordDto {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    public ProductRecordDto() {
    }

    public ProductRecordDto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
