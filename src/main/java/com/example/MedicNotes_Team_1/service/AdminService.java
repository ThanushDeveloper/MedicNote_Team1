package com.example.MedicNotes_Team_1.service;

import com.example.MedicNotes_Team_1.entity.Admin;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<String> registerAdmin(Admin admin);
}
