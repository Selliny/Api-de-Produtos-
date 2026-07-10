package com.selliny.productapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.selliny.productapi.models.Product;

@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    void shouldPersistProduct() {
        Product product = new Product();
        product.setName("Headset");
        product.setPrice(250.0);

        Product savedProduct = productsRepository.save(product);

        assertTrue(savedProduct.getId() != null);
        assertEquals("Headset", savedProduct.getName());
        assertEquals(250.0, savedProduct.getPrice());
    }
}
