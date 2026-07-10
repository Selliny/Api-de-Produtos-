package Teste.application.test.services;

import java.util.List;

import Teste.application.test.models.Product;

public interface ProductsServices {
    Product save(Product product);

    List<Product> findAll();

    Product findById(Long id);

    Product update(Product product);

    void deleteById(Long id);
}
