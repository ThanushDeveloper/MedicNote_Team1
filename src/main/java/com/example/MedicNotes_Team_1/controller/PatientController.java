package com.example.MedicNotes_Team_1.controller;
// package: com.example.MedicNotes_Team_1.controller


import com.example.MedicNotes_Team_1.entity.Patient;
import com.example.MedicNotes_Team_1.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // ✅ Add Patient
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<String> addPatient(
            @RequestPart("patient") Patient patient,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        String result = patientService.addPatient(patient, image);
        return ResponseEntity.ok(result);
    }

    // ✅ Get All Patients
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients Registered Today
    @GetMapping("/registered-today")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredToday() {
        List<Patient> patients = patientService.getPatientsRegisteredToday();
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients Registered Yesterday
    @GetMapping("/registered-yesterday")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredYesterday() {
        List<Patient> patients = patientService.getPatientsRegisteredYesterday();
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients Registered This Week
    @GetMapping("/registered-this-week")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredThisWeek() {
        List<Patient> patients = patientService.getPatientsRegisteredThisWeek();
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients Registered This Month
    @GetMapping("/registered-this-month")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredThisMonth() {
        List<Patient> patients = patientService.getPatientsRegisteredThisMonth();
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients by Gender
    @GetMapping("/by-gender/{gender}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsByGender(@PathVariable Patient.Gender gender) {
        List<Patient> patients = patientService.getPatientsByGender(gender);
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients by Name
    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsByName(@PathVariable String name) {
        List<Patient> patients = patientService.getPatientsByName(name);
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients by Phone
    @GetMapping("/by-phone/{phone}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsByPhone(@PathVariable String phone) {
        List<Patient> patients = patientService.getPatientsByPhone(phone);
        return ResponseEntity.ok(patients);
    }

    // ✅ Get Patients Registered on Specific Date (format: yyyy-MM-dd)
    @GetMapping("/registered-on/{date}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredOnDate(@PathVariable String date) {
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            List<Patient> patients = patientService.getPatientsRegisteredOnDate(parsedDate);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ✅ Get Patients by Treatment
    @GetMapping("/by-treatment/{treatment}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getPatientsByTreatment(@PathVariable String treatment) {
        List<Patient> patients = patientService.getPatientsByTreatment(treatment);
        return ResponseEntity.ok(patients);
    }

    // ✅ Update Patient by ID
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<String> updatePatient(
            @PathVariable Long id,
            @RequestPart("patient") Patient patient,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        String result = patientService.updatePatient(id, patient, image);
        return ResponseEntity.ok(result);
    }

    // ✅ Delete Patient by ID
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient with ID " + id + " deleted successfully.");
    }
}
