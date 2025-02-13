package com.sportyshoes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper; // Used for JSON serialization

    @Test
    void testGetProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        Category category = new Category("Running");
        category.setId(1L);
        Product product = new Product("Running Shoe A", 100.0, category);
        product.setId(1L);
        products.add(product);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/admin/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Running Shoe A")));
    }

    @Test
    void testCreateProduct() throws Exception {
        Category category = new Category("Running");
        category.setId(1L);
        Product product = new Product("Soccer Cleats", 120.0, category);
        product.setId(1L);

        when(productService.createProduct(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Soccer Cleats")));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Category category = new Category("Running");
        category.setId(1L);
        Product product = new Product("Updated Shoe", 130.0, category);
        product.setId(1L);

        when(productService.updateProduct(eq(1L), org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/admin/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Shoe")));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1L);
        mockMvc.perform(delete("/admin/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully."));
    }
}
