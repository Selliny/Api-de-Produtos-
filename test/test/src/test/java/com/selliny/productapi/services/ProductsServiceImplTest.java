package com.selliny.productapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.selliny.productapi.models.Product;
import com.selliny.productapi.repository.ProductsRepository;

@ExtendWith(MockitoExtension.class)
class ProductsServiceImplTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsServiceImpl productsService;

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = List.of(new Product(1L, "Mouse", 80.0));
        when(productsRepository.findAll()).thenReturn(products);

        List<Product> result = productsService.findAll();

        assertEquals(products, result);
    }

    @Test
    void shouldReturnProductById() {
        Product product = new Product(1L, "Keyboard", 120.0);
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productsService.findById(1L);

        assertEquals(product, result);
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        when(productsRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productsService.findById(10L));
    }

    @Test
    void shouldDeleteExistingProduct() {
        Product product = new Product(2L, "Monitor", 900.0);
        when(productsRepository.findById(2L)).thenReturn(Optional.of(product));

        productsService.deleteById(2L);

        verify(productsRepository).delete(product);
    }
}
