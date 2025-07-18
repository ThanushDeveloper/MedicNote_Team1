package com.example.MedicNotes_Team_1.controller;

import com.example.MedicNotes_Team_1.entity.Prescription;
import com.example.MedicNotes_Team_1.service.PrescriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/add")
    public String addPrescription(@RequestPart("prescription") String prescriptionJson,
                                  @RequestParam Long doctorId,
                                  @RequestParam Long patientId,
                                  @RequestPart(value = "images", required = false) MultipartFile[] images) {
        try {
            Prescription prescription = objectMapper.readValue(prescriptionJson, Prescription.class);
            return prescriptionService.addPrescription(prescription, doctorId, patientId, images);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }











}
