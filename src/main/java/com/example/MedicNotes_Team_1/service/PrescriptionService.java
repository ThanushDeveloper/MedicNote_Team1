package com.example.MedicNotes_Team_1.service;

import com.example.MedicNotes_Team_1.entity.Prescription;
import org.springframework.web.multipart.MultipartFile;

public interface PrescriptionService {
    String addPrescription(Prescription prescription, Long doctorId, Long patientId, MultipartFile[] images);




}
