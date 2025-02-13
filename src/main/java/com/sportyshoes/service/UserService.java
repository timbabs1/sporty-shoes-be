package com.sportyshoes.service;

import com.sportyshoes.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> searchUsers(String keyword);

    // New CRUD methods for users
    User createUser(User user);

    User updateUser(Long userId, User user) throws Exception;

    void deleteUser(Long userId) throws Exception;
    
    User login(String username, String password) throws Exception;

}
