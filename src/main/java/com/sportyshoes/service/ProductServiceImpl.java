package com.sportyshoes.service;

import com.sportyshoes.model.Product;
import com.sportyshoes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long productId, Product productDetails) throws Exception {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setImageUrl(productDetails.getImageUrl());
            return productRepository.save(product);
        }
        throw new Exception("Product not found");
    }

    @Override
    public void deleteProduct(Long productId) throws Exception {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            productRepository.delete(productOpt.get());
        } else {
            throw new Exception("Product not found");
        }
    }
}
