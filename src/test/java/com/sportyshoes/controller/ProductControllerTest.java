package com.sportyshoes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.repository.CategoryRepository;
import com.sportyshoes.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProductWithImage() throws Exception {
        // Create a sample category and product
        Category category = new Category("Running");
        category.setId(1L);
        Product product = new Product("Soccer Cleats", 120.0, category, "sporty-shoes-images/soccer_cleats.jpg");
        product.setId(1L);

        // Prepare JSON part for product data
        String productJson = objectMapper.writeValueAsString(new Product("Soccer Cleats", 120.0, category, null));
        MockMultipartFile productPart = new MockMultipartFile("product", "", "application/json", productJson.getBytes());

        // Prepare a fake image file part (using a file from classpath for example)
        ClassPathResource imageResource = new ClassPathResource("test-data/soccer_cleats.jpg");
        InputStream imageStream = imageResource.getInputStream();
        MockMultipartFile imagePart = new MockMultipartFile("image", "soccer_cleats.jpg", "image/jpeg", imageStream);

        // When productService.createProduct is called, return our product with imageUrl set.
        when(productService.createProduct(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            // Simulate that the controller set imageUrl after processing the file.
            p.setImageUrl("sporty-shoes-images/soccer_cleats.jpg");
            p.setId(1L);
            return p;
        });

        mockMvc.perform(multipart("/admin/products")
                        .file(productPart)
                        .file(imagePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Soccer Cleats")))
                .andExpect(jsonPath("$.imageUrl", is("sporty-shoes-images/soccer_cleats.jpg")));
    }

    @Test
    void testUpdateProductWithImage() throws Exception {
        // Setup an existing product
        Category category = new Category("Running");
        category.setId(1L);
        Product product = new Product("Old Cleats", 100.0, category, null);
        product.setId(1L);

        // New updated data without the image set yet.
        Product updatedProduct = new Product("Updated Cleats", 110.0, category, null);
        updatedProduct.setId(1L);

        String productJson = objectMapper.writeValueAsString(updatedProduct);
        MockMultipartFile productPart = new MockMultipartFile("product", "", "application/json", productJson.getBytes());

        // Prepare a fake image file part.
        ClassPathResource imageResource = new ClassPathResource("test-data/updated_cleats.jpg");
        InputStream imageStream = imageResource.getInputStream();
        MockMultipartFile imagePart = new MockMultipartFile("image", "updated_cleats.jpg", "image/jpeg", imageStream);

        // When updating, simulate that the productService updates the product and sets imageUrl.
        when(productService.updateProduct(eq(1L), any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(1);
            p.setImageUrl("sporty-shoes-images/updated_cleats.jpg");
            return p;
        });

        mockMvc.perform(multipart("/admin/products/1")
                        .file(productPart)
                        .file(imagePart)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Cleats")))
                .andExpect(jsonPath("$.imageUrl", is("sporty-shoes-images/updated_cleats.jpg")));
    }
}
