package com.sportyshoes.controller;

import com.sportyshoes.model.Purchase;
import com.sportyshoes.model.User;
import com.sportyshoes.model.Product;
import com.sportyshoes.repository.PurchaseRepository;
import com.sportyshoes.repository.UserRepository;
import com.sportyshoes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        // Validate the user exists
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
        LocalDate today = LocalDate.now();
        // For each item in the order, create a Purchase record
        for (OrderItem item : orderRequest.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new Exception("Product not found with id " + item.getProductId()));
            Purchase purchase = new Purchase(user, product, today, item.getQuantity());
            purchaseRepository.save(purchase);
        }
        return "Order placed successfully";
    }
}

// DTO classes for order payload
class OrderRequest {
    private Long userId;
    private List<OrderItem> items;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}

class OrderItem {
    private Long productId;
    private int quantity;

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
