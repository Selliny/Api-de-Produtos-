package com.selliny.productapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selliny.productapi.models.Product;
import com.selliny.productapi.services.ProductsServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Operations for product management")
public class ProductsController {

    private final ProductsServices productsService;

    public ProductsController(ProductsServices productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    @Operation(summary = "List all products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productsService.findAll();
        products.forEach(product -> product.add(
                linkTo(methodOn(ProductsController.class).getOneProduct(product.getId())).withSelfRel()));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one product by id")
    public ResponseEntity<Product> getOneProduct(@PathVariable Long id) {
        Product product = productsService.findById(id);
        product.add(linkTo(methodOn(ProductsController.class).getAllProducts()).withRel("products"));
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        Product product = new Product();
        product.setName(productRecordDto.getName());
        product.setPrice(productRecordDto.getPrice());
        Product savedProduct = productsService.save(product);
        savedProduct.add(linkTo(methodOn(ProductsController.class).getOneProduct(savedProduct.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody @Valid ProductRecordDto productRecordDto) {
        Product existingProduct = productsService.findById(id);
        existingProduct.setName(productRecordDto.getName());
        existingProduct.setPrice(productRecordDto.getPrice());
        Product updatedProduct = productsService.update(existingProduct);
        updatedProduct.add(linkTo(methodOn(ProductsController.class).getOneProduct(updatedProduct.getId())).withSelfRel());
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
