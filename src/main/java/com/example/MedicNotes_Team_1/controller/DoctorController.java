package com.example.MedicNotes_Team_1.controller;

import com.example.MedicNotes_Team_1.entity.Doctor;
import com.example.MedicNotes_Team_1.repository.DoctorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    // Registor doctor by admin
    @PostMapping("/register-doctor")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> registerDoctor(
            @RequestPart("doctor") String doctorJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Doctor doctor = objectMapper.readValue(doctorJson, Doctor.class);

        if (doctorRepository.findByEmail(doctor.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already exists in another account.");
        }
        if (doctorRepository.findByPhone(doctor.getPhone()) != null) {
            return ResponseEntity.badRequest().body("Phone number already exists in another account.");
        }

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

        if (image != null && !image.isEmpty()) {
            doctor.setDoctor_image(image.getBytes());
        }

        doctor.setCreated_at(new Date());
        doctor.setUpdated_at(new Date());

        doctorRepository.save(doctor);
        return ResponseEntity.ok("Doctor registered successfully by admin.");
    }

    // get all doctor by pagining
    @GetMapping("/AllDoctors")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllDoctorsByAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Doctor> doctors = doctorRepository.findAll(pageable);

        if (doctors.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No doctors found for the requested page and size.");
        }

        List<Map<String, Object>> simplifiedDoctors = doctors.stream().map(doctor -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", doctor.getId());
            data.put("name", doctor.getName());
            data.put("email", doctor.getEmail());
            data.put("phone", doctor.getPhone());
            data.put("specialization", doctor.getSpecialization());
            data.put("gender", doctor.getGender());
            data.put("dob", doctor.getDob());
            data.put("status", doctor.getStatus());
            return data;
        }).toList();

        return ResponseEntity.ok(simplifiedDoctors);
    }


    // Get doctor by DoctorId
    @GetMapping("/ByDoctorId/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor == null) {
            return ResponseEntity
                    .status(404)
                    .body("No doctor found with ID: " + id);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", doctor.getId());
        data.put("name", doctor.getName());
        data.put("email", doctor.getEmail());
        data.put("phone", doctor.getPhone());
        data.put("specialization", doctor.getSpecialization());
        data.put("gender", doctor.getGender());
        data.put("dob", doctor.getDob());
        data.put("status", doctor.getStatus());

        return ResponseEntity.ok(data);
    }



    // Get doctor by name
    @GetMapping("/ByDoctorName/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorsByName(@PathVariable String name) {
        List<Doctor> doctors = doctorRepository.findByName(name);

        if (doctors.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No doctors found with the name: " + name);
        }

        List<Map<String, Object>> response = doctors.stream().map(doctor -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", doctor.getId());
            data.put("name", doctor.getName());
            data.put("email", doctor.getEmail());
            data.put("phone", doctor.getPhone());
            data.put("specialization", doctor.getSpecialization());
            data.put("gender", doctor.getGender());
            data.put("dob", doctor.getDob());
            data.put("status", doctor.getStatus());
            return data;
        }).toList();

        return ResponseEntity.ok(response);
    }




    // Get doctor by email
    @GetMapping("/ByDoctorEmail/{email}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorByEmail(@PathVariable String email) {
        Doctor doctor = doctorRepository.findByEmail(email);

        if (doctor == null) {
            return ResponseEntity
                    .status(404)
                    .body("No doctor found with the email: " + email);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", doctor.getId());
        response.put("name", doctor.getName());
        response.put("email", doctor.getEmail());
        response.put("phone", doctor.getPhone());
        response.put("specialization", doctor.getSpecialization());
        response.put("gender", doctor.getGender());
        response.put("dob", doctor.getDob());
        response.put("status", doctor.getStatus());

        return ResponseEntity.ok(response);
    }



    // Get doctor by phone
    @GetMapping("/ByDoctorPhone/{phone}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorByPhone(@PathVariable String phone) {
        Doctor doctor = doctorRepository.findByPhone(phone);

        if (doctor == null) {
            return ResponseEntity
                    .status(404)
                    .body("No doctor found with the phone number: " + phone);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", doctor.getId());
        response.put("name", doctor.getName());
        response.put("email", doctor.getEmail());
        response.put("phone", doctor.getPhone());
        response.put("specialization", doctor.getSpecialization());
        response.put("gender", doctor.getGender());
        response.put("dob", doctor.getDob());
        response.put("status", doctor.getStatus());

        return ResponseEntity.ok(response);
    }



    // Get doctors by gender
    @GetMapping("/ByDoctorGender/{gender}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorsByGender(@PathVariable Doctor.Gender gender) {
        List<Doctor> doctors = doctorRepository.findByGender(gender);

        if (doctors.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No doctors found with gender: " + gender);
        }

        List<Map<String, Object>> doctorList = doctors.stream()
                .map(doc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", doc.getId());
                    map.put("name", doc.getName());
                    map.put("email", doc.getEmail());
                    map.put("phone", doc.getPhone());
                    map.put("specialization", doc.getSpecialization());
                    map.put("gender", doc.getGender());
                    map.put("dob", doc.getDob());
                    map.put("status", doc.getStatus());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorList);
    }



    // Get doctors by specialization
    @GetMapping("/ByDoctorSpecialization/{specialization}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization);

        if (doctors.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No doctors found with specialization: " + specialization);
        }

        List<Map<String, Object>> doctorList = doctors.stream()
                .map(doc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", doc.getId());
                    map.put("name", doc.getName());
                    map.put("email", doc.getEmail());
                    map.put("phone", doc.getPhone());
                    map.put("specialization", doc.getSpecialization());
                    map.put("gender", doc.getGender());
                    map.put("dob", doc.getDob());
                    map.put("status", doc.getStatus());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorList);
    }



    // Get doctors by status
    @GetMapping("/ByDoctorStatus/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getDoctorsByStatus(@PathVariable Doctor.Status status) {
        List<Doctor> doctors = doctorRepository.findByStatus(status);

        if (doctors.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No doctors found with status: " + status);
        }

        List<Map<String, Object>> doctorList = doctors.stream()
                .map(doc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", doc.getId());
                    map.put("name", doc.getName());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorList);
    }



    // Get all distinct specializations
    @GetMapping("/DoctorsAllSpecializations")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllDistinctSpecializations() {
        List<String> specializations = doctorRepository.findDistinctSpecializations();

        if (specializations.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No specializations found in the system.");
        }

        return ResponseEntity.ok(specializations);
    }


    // Update doctor status
    @PutMapping("/UpdateDoctorStatus/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateDoctorStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor with ID " + id + " not found.");
        }

        try {
            Doctor.Status newStatus = Doctor.Status.valueOf(status.toUpperCase());
            doctor.setStatus(newStatus);
            doctor.setUpdated_at(new Date());
            doctorRepository.save(doctor);
            return ResponseEntity.ok("Doctor status updated to " + newStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status. Use ACTIVE or INACTIVE.");
        }
    }

    // Update doctor fully
    @PutMapping("/UpdateDoctor/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor updatedDoctor) {

        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor == null) {
            return ResponseEntity.badRequest().body("Doctor with ID " + id + " not found.");
        }

        doctor.setName(updatedDoctor.getName());
        doctor.setEmail(updatedDoctor.getEmail());
        doctor.setPhone(updatedDoctor.getPhone());
        doctor.setGender(updatedDoctor.getGender());
        doctor.setDob(updatedDoctor.getDob());
        doctor.setSpecialization(updatedDoctor.getSpecialization());
        doctor.setStatus(updatedDoctor.getStatus());
        doctor.setUpdated_at(new Date());

        doctorRepository.save(doctor);
        return ResponseEntity.ok("Doctor with ID " + id + " updated successfully.");
    }

    // Delete doctor by ID
    @DeleteMapping("/deleteDoctor/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctorRepository.deleteById(id);
                    return ResponseEntity.ok("Doctor with ID " + id + " has been deleted successfully.");
                })
                .orElse(ResponseEntity.status(404).body("Doctor with ID " + id + " not found."));
    }


}
