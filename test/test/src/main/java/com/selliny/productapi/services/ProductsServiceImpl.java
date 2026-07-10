package com.selliny.productapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selliny.productapi.models.Product;
import com.selliny.productapi.repository.ProductsRepository;

@Service
public class ProductsServiceImpl implements ProductsServices {

    private final ProductsRepository productsRepository;

    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public Product save(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productsRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product update(Product product) {
        return productsRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = findById(id);
        productsRepository.delete(product);
    }
}
