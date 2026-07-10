package Teste.application.test.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import Teste.application.test.models.Product;
import Teste.application.test.services.ProductNotFoundException;
import Teste.application.test.services.ProductsServices;

@WebMvcTest(ProductsController.class)
@Import(RestExceptionHandler.class)
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsServices productsServices;

    @Test
    void shouldListProducts() throws Exception {
        when(productsServices.findAll()).thenReturn(List.of(new Product(1L, "Mouse", 79.9)));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Mouse"))
                .andExpect(jsonPath("$[0].price").value(79.9));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        when(productsServices.save(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenReturn(new Product(1L, "Keyboard", 120.0));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Keyboard\",\"price\":120.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Keyboard"))
                .andExpect(jsonPath("$.price").value(120.0));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        when(productsServices.findById(99L)).thenThrow(new ProductNotFoundException(99L));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found with id 99"));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        when(productsServices.findById(1L)).thenReturn(new Product(1L, "Mouse", 79.9));
        when(productsServices.update(org.mockito.ArgumentMatchers.any(Product.class)))
                .thenReturn(new Product(1L, "Mouse Gamer", 99.9));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mouse Gamer\",\"price\":99.9}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mouse Gamer"))
                .andExpect(jsonPath("$.price").value(99.9));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productsServices).deleteById(1L);
    }
}
