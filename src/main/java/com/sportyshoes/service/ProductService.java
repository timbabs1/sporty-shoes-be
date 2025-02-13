package com.sportyshoes.service;

import com.sportyshoes.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product updateProduct(Long productId, Product product) throws Exception;
    void deleteProduct(Long productId) throws Exception;
}
