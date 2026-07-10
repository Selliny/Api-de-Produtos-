package com.selliny.productapi.services;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product not found with id " + id);
    }
}
