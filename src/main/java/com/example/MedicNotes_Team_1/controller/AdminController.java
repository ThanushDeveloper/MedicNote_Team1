package com.example.MedicNotes_Team_1.controller;

import com.example.MedicNotes_Team_1.entity.Admin;
import com.example.MedicNotes_Team_1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        return adminService.registerAdmin(admin);
    }
}
