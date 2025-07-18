package com.example.MedicNotes_Team_1.controller;


import com.example.MedicNotes_Team_1.dto.LoginRequest;
import com.example.MedicNotes_Team_1.dto.LoginResponse;
import com.example.MedicNotes_Team_1.entity.*;
import com.example.MedicNotes_Team_1.repository.*;
import com.example.MedicNotes_Team_1.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminRepository adminRepo;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;
    private final JwtUtil jwtUtil;


    @Autowired
    private PasswordEncoder passwordEncoder;

    // admin login
    @PostMapping("/admin/loginById")
    public ResponseEntity<?> adminLoginById(@RequestBody LoginRequest request) {
        Admin admin = adminRepo.findById(request.getAdminId()).orElse(null);
        if (admin != null && admin.getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(String.valueOf(admin.getId()), "ADMIN");
            return ResponseEntity.ok("Login successful. Token: " + token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials. Please check your admin ID and password.");
    }

    @PostMapping("/admin/loginByGmail")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest request) {
        Admin admin = adminRepo.findByEmail(request.getEmail());
        if (admin != null && admin.getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(admin.getEmail(), "ADMIN");
            return ResponseEntity.ok("Login successful. Token: " + token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials. Please check your email and password.");
    }

    @PostMapping("/admin/loginByPhone")
    public ResponseEntity<?> adminLoginByPhone(@RequestBody LoginRequest request) {
        Admin admin = adminRepo.findByPhone(request.getPhone());
        if (admin != null && admin.getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(admin.getPhone(), "ADMIN");
            return ResponseEntity.ok("Login successful. Token: " + token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials. Please check your phone and password.");
    }





    @PostMapping("/doctor/login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest request) {
        Doctor doctor = doctorRepo.findByEmail(request.getEmail());
        if (doctor != null && doctor.getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(doctor.getEmail(), "DOCTOR");
            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/patient/login")
    public ResponseEntity<LoginResponse> patientLogin(@RequestBody LoginRequest request) {
        Patient patient = patientRepo.findByEmail(request.getEmail())
                .orElse(null);

        if (patient != null && passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
            String token = jwtUtil.generateToken(patient.getEmail(), "PATIENT");
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.badRequest().build();
    }



}
