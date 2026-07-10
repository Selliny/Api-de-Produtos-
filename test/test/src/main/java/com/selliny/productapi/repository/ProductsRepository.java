package com.selliny.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selliny.productapi.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    
}
