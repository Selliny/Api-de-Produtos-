package Teste.application.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Teste.application.test.models.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    
}
