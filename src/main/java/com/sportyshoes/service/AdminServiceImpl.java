package com.sportyshoes.service;

import com.sportyshoes.model.Admin;
import com.sportyshoes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin login(String username, String password) throws Exception {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new Exception("Invalid password");
            }
        }
        throw new Exception("Admin not found");
    }

    @Override
    public Admin changePassword(Long adminId, String newPassword) throws Exception {
        Optional<Admin> adminOpt = adminRepository.findById(adminId);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            admin.setPassword(newPassword);
            return adminRepository.save(admin);
        }
        throw new Exception("Admin not found");
    }
}
