package com.example.MedicNotes_Team_1.controller;

import com.example.MedicNotes_Team_1.entity.Patient;
import com.example.MedicNotes_Team_1.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        return ResponseEntity.ok("Registration successful! Your account has been created.");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DOCTOR')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/registered-today")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredToday() {
        return ResponseEntity.ok(patientService.getPatientsRegisteredToday());
    }

    @GetMapping("/registered-yesterday")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredYesterday() {
        return ResponseEntity.ok(patientService.getPatientsRegisteredYesterday());
    }

    @GetMapping("/registered-this-week")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredThisWeek() {
        return ResponseEntity.ok(patientService.getPatientsRegisteredThisWeek());
    }

    @GetMapping("/registered-this-month")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredThisMonth() {
        return ResponseEntity.ok(patientService.getPatientsRegisteredThisMonth());
    }

    @GetMapping("/by-gender/{gender}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsByGender(@PathVariable Patient.Gender gender) {
        return ResponseEntity.ok(patientService.getPatientsByGender(gender));
    }

    @GetMapping("/by-name/{name}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsByName(@PathVariable String name) {
        return ResponseEntity.ok(patientService.getPatientsByName(name));
    }

    @GetMapping("/by-phone/{phone}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(patientService.getPatientsByPhone(phone));
    }

    @GetMapping("/registered-on/{date}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsRegisteredOnDate(@PathVariable String date) throws ParseException {
        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        return ResponseEntity.ok(patientService.getPatientsRegisteredOnDate(parsedDate));
    }

    @GetMapping("/by-treatment/{treatment}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Patient>> getPatientsByTreatment(@PathVariable String treatment) {
        return ResponseEntity.ok(patientService.getPatientsByTreatment(treatment));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        patientService.updatePatient(id, patient);
        return ResponseEntity.ok("Patient updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Patient with ID " + id + " deleted successfully.");
    }
}