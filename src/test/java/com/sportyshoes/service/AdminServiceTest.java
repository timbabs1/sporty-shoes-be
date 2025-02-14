package com.sportyshoes.service;

import com.sportyshoes.model.Admin;
import com.sportyshoes.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void testLoginSuccess() throws Exception {
        // Assumes DataLoader has created an admin with username "admin" and password "test123"
        Admin admin = adminService.login("admin", "test123");
        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
    }

    @Test
    void testLoginFailureWrongPassword() {
        Exception exception = assertThrows(Exception.class, () ->
                adminService.login("admin", "wrongpassword"));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void testLoginNonExistentAdmin() {
        Exception exception = assertThrows(Exception.class, () ->
                adminService.login("nonexistent", "whatever"));
        assertEquals("Admin not found", exception.getMessage());
    }

    @Test
    void testChangePassword() throws Exception {
        Admin admin = adminService.login("admin", "test123");
        Admin updatedAdmin = adminService.changePassword(admin.getId(), "newpassword");
        assertEquals("newpassword", updatedAdmin.getPassword());
    }
}
