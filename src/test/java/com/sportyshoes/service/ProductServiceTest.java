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
    void testCreateProductWithImageUrl() {
        List<Category> categories = categoryRepository.findAll();
        assertFalse(categories.isEmpty(), "At least one category should exist");
        Category category = categories.get(0);

        // Set an imageUrl manually since the service doesn't process file upload
        Product product = new Product("Test Shoe", 99.99, category, "sporty-shoes-images/test_shoe.jpg");
        Product created = productService.createProduct(product);
        assertNotNull(created.getId());
        assertEquals("Test Shoe", created.getName());
        assertEquals("sporty-shoes-images/test_shoe.jpg", created.getImageUrl());
    }

    @Test
    void testUpdateProductWithImageUrl() throws Exception {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.get(0);
        Product product = new Product("Old Name", 50.0, category, null);
        Product created = productService.createProduct(product);

        // Update product details, including setting an imageUrl
        created.setName("New Name");
        created.setPrice(75.0);
        created.setImageUrl("sporty-shoes-images/new_shoe.jpg");
        Product updated = productService.updateProduct(created.getId(), created);
        assertEquals("New Name", updated.getName());
        assertEquals(75.0, updated.getPrice());
        assertEquals("sporty-shoes-images/new_shoe.jpg", updated.getImageUrl());
    }
}
