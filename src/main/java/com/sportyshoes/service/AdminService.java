package com.sportyshoes.service;

import com.sportyshoes.model.Admin;

public interface AdminService {
    Admin login(String username, String password) throws Exception;
    Admin changePassword(Long adminId, String newPassword) throws Exception;
}