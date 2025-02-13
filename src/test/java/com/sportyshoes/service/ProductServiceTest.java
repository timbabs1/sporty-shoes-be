package com.sportyshoes.service;

import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.repository.CategoryRepository;
import com.sportyshoes.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateProduct() {
        List<Category> categories = categoryRepository.findAll();
        assertFalse(categories.isEmpty(), "At least one category should exist");
        Category category = categories.get(0);

        Product product = new Product("Test Shoe", 99.99, category);
        Product created = productService.createProduct(product);
        assertNotNull(created.getId());
        assertEquals("Test Shoe", created.getName());
    }

    @Test
    void testUpdateProduct() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.get(0);
        Product product = new Product("Old Name", 50.0, category);
        Product created = productService.createProduct(product);

        // Update product details
        created.setName("New Name");
        created.setPrice(75.0);
        Product updated = productService.updateProduct(created.getId(), created);
        assertEquals("New Name", updated.getName());
        assertEquals(75.0, updated.getPrice());
    }

    @Test
    void testDeleteProduct() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.get(0);
        Product product = new Product("Delete Me", 20.0, category);
        Product created = productService.createProduct(product);
        Long id = created.getId();

        productService.deleteProduct(id);
        assertFalse(productRepository.findById(id).isPresent());
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        // DataLoader may have pre-loaded some products.
        assertTrue(products.size() >= 1);
    }
}
