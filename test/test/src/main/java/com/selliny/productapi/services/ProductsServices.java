package com.selliny.productapi.services;

import java.util.List;

import com.selliny.productapi.models.Product;

public interface ProductsServices {
    Product save(Product product);

    List<Product> findAll();

    Product findById(Long id);

    Product update(Product product);

    void deleteById(Long id);
}
