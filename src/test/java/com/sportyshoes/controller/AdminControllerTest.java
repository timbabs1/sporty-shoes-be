package com.sportyshoes.controller;

import com.sportyshoes.model.Admin;
import com.sportyshoes.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    void testLoginSuccess() throws Exception {
        Admin admin = new Admin("admin", "admin123");
        admin.setId(1L);
        when(adminService.login("admin", "admin123")).thenReturn(admin);

        mockMvc.perform(post("/admin/login")
                        .param("username", "admin")
                        .param("password", "admin123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"));
    }

    @Test
    void testChangePassword() throws Exception {
        Admin admin = new Admin("admin", "newpassword");
        admin.setId(1L);
        when(adminService.changePassword(1L, "newpassword")).thenReturn(admin);

        mockMvc.perform(put("/admin/change-password")
                        .param("adminId", "1")
                        .param("newPassword", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("newpassword"));
    }
}
