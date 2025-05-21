package Teste.application.test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import Teste.application.test.repository.ProductsRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Teste.application.test.models.Product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Id;





@RestController
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(List<Product> productsList, List<Product> productList) {
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                Long id = product.getIdProduct();
                product.add (linkTo(methodOn(ProductsController.class).getOneProduct(id)).withSelfRel());
             }    
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }
    

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProducts(@PathVariable(value = "id")
            Long idLong) {
        Optional<Product> productOptional = productsRepository.findById(idLong);
        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Products not found");
        }
        productOptional.get().add(linkTo(methodOn(ProductsController.class).getAllProduct()).withRel("Product List"));
        return ResponseEntity.status(HttpStatus.OK).body(productOptional.get());
    
    }
    
    @PostMapping("/products")
    public ResponseEntity<Product> saveProducts (@RequestBody @Valid ProductRecordDto productRecordDto) {
        var products = new Product();
        BeanUtils.copyProperties(productRecordDto, products);
        return ResponseEntity.status(HttpStatus.CREATED).body(productsRepository.save(products));
    }
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") Long id) {
		Optional<Product> productO = productsRepository.findById(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		productsRepository.delete(productO.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") Long id,
													  @RequestBody @Valid ProductRecordDto productRecordDto) {
		Optional<Product> productO = productsRepository.findById(id);
		if(productO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
		var productModel = productO.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productsRepository.save(productModel));
	}

    private Class<?> getAllProduct() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

