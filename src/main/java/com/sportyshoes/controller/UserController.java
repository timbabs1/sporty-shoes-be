package com.sportyshoes.controller;

import com.sportyshoes.model.User;
import com.sportyshoes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Optionally pass a search keyword as query parameter (e.g., /admin/users?keyword=john)
    @GetMapping
    public List<User> getUsers(@RequestParam(required = false) String keyword) {
        return userService.searchUsers(keyword);
    }

    // New endpoint: Create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // New endpoint: Update an existing user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        return userService.updateUser(id, user);
    }

    // New endpoint: Delete an existing user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) throws Exception {
        userService.deleteUser(id);
        return "User deleted successfully.";
    }

    @PostMapping("/login")
    public User loginUser(@RequestParam String username, @RequestParam String password) throws Exception {
        return userService.login(username, password);
    }
}
