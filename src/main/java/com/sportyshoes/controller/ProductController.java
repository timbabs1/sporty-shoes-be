// src/main/java/com/sportyshoes/controller/ProductController.java
package com.sportyshoes.controller;

import com.sportyshoes.model.Product;
import com.sportyshoes.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/admin/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    // Inject the directory path from application.properties (or use a default)
    @Value("${product.image.upload-dir:sporty-shoes-images}")
    private String uploadDir;

    @GetMapping
    public java.util.List<Product> getProducts() {
        return productService.getAllProducts();
    }

    // Updated to accept multipart/form-data for product creation
    @PostMapping(consumes = {"multipart/form-data"})
    public Product createProduct(
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {

        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = storeImage(imageFile);
            product.setImageUrl(imagePath);
        }
        return productService.createProduct(product);
    }

    // Updated update endpoint to optionally accept an image file
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Product updateProduct(
            @PathVariable Long id,
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {
        log.info("Image file: {}", imageFile);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = storeImage(imageFile);
            log.info("Image uploaded to: {}", imagePath);
            product.setImageUrl(imagePath);
        }
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) throws Exception {
        productService.deleteProduct(id);
        return "Product deleted successfully.";
    }

    // Utility method to store the image file and return its relative path.
    private String storeImage(MultipartFile file) throws IOException {
        // Clean file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Create directory if not exists
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // Resolve the file path (for example: sporty-shoes-images/filename.jpg)
        Path filePath = uploadPath.resolve(fileName);
        // Copy file to the target location (overwriting existing file with the same name)
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        // Return the relative path
        return uploadDir + "/" + fileName;
    }
}
