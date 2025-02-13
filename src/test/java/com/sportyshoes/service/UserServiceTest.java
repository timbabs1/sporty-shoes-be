package com.sportyshoes.service;

import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertNotNull(users);
        // DataLoader pre-loads at least 2 users.
        assertTrue(users.size() >= 2);
    }

    @Test
    void testSearchUsers() {
        List<User> users = userService.searchUsers("john");
        assertNotNull(users);
        assertFalse(users.isEmpty());
        for (User u : users) {
            assertTrue(u.getUsername().toLowerCase().contains("john"));
        }
    }
}
