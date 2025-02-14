package com.sportyshoes.controller;

import com.sportyshoes.model.Admin;
import com.sportyshoes.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Simple login endpoint
    @PostMapping("/login")
    public Admin login(@RequestParam String username, @RequestParam String password) throws Exception {
        return adminService.login(username, password);
    }

    // Change password endpoint
    @PutMapping("/change-password")
    public Admin changePassword(@RequestParam Long adminId, @RequestParam String newPassword) throws Exception {
        return adminService.changePassword(adminId, newPassword);
    }
}