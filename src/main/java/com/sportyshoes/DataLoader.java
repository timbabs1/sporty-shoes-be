package com.sportyshoes;

import com.sportyshoes.model.*;
import com.sportyshoes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize an admin if none exists
        if (adminRepository.count() == 0) {
            adminRepository.save(new Admin("admin", "admin123"));
        }

        // Initialize some categories and products
        if (categoryRepository.count() == 0) {
            Category running = new Category("Running");
            Category basketball = new Category("Basketball");
            categoryRepository.save(running);
            categoryRepository.save(basketball);

            productRepository.save(new Product("Running Shoe A", 100.0, running, "running_shoe_a.jpg"));
            productRepository.save(new Product("Basketball Shoe B", 150.0, basketball, "basketball_shoe_b.jpg"));
        }

        // Initialize some users
        if (userRepository.count() == 0) {
            userRepository.save(new User("john_doe", "john@example.com", "test123"));
            userRepository.save(new User("jane_doe", "jane@example.com", "test123"));
        }

        // Create sample purchase records for reporting if none exist
        if (purchaseRepository.count() == 0) {
            // Retrieve all products and users
            List<Product> products = productRepository.findAll();
            List<User> users = userRepository.findAll();

            // Ensure we have at least two products and two users
            if (products.size() >= 2 && users.size() >= 2) {
                Product product1 = products.get(0); // Running Shoe A (Category: Running)
                Product product2 = products.get(1); // Basketball Shoe B (Category: Basketball)
                User user1 = users.get(0);          // john_doe
                User user2 = users.get(1);          // jane_doe

                // Create a few purchase entries with different dates for reporting
                purchaseRepository.save(new Purchase(user1, product1, LocalDate.of(2025, 1, 10), 1));
                purchaseRepository.save(new Purchase(user2, product2, LocalDate.of(2025, 1, 15), 1));
                purchaseRepository.save(new Purchase(user1, product2, LocalDate.of(2025, 2, 5), 1));
                purchaseRepository.save(new Purchase(user2, product1, LocalDate.of(2025, 2, 20), 1));
            }
        }
    }
}