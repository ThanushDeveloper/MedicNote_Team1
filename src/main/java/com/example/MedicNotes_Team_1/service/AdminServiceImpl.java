package com.example.MedicNotes_Team_1.service;

import com.example.MedicNotes_Team_1.entity.Admin;
import com.example.MedicNotes_Team_1.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public ResponseEntity<String> registerAdmin(Admin admin) {
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin registered successfully.");
    }
}
