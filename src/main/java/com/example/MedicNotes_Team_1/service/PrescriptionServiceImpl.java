package com.example.MedicNotes_Team_1.service;


import com.example.MedicNotes_Team_1.entity.*;
import com.example.MedicNotes_Team_1.repository.DoctorRepository;
import com.example.MedicNotes_Team_1.repository.PatientRepository;
import com.example.MedicNotes_Team_1.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public String addPrescription(Prescription prescription, Long doctorId, Long patientId, MultipartFile[] images) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor with id " + doctorId + " not found"));

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient with id " + patientId + " not found"));

        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setCreated_at(new Date());
        prescription.setUpdated_at(new Date());
        prescription.setPrescription_date(new Date()); // take current date instead of user input

        // Link medicines
        if (prescription.getMedicines() != null) {
            for (Medicine medicine : prescription.getMedicines()) {
                medicine.setPrescription(prescription);
            }
        }

        // Handle images
        if (images != null && images.length > 0) {
            List<PrescriptionImage> prescriptionImages = new ArrayList<>();
            for (MultipartFile file : images) {
                try {
                    PrescriptionImage img = new PrescriptionImage();
                    img.setImageData(file.getBytes());
                    img.setPrescription(prescription);
                    prescriptionImages.add(img);
                } catch (IOException e) {
                    throw new RuntimeException("Error reading prescription image: " + e.getMessage());
                }
            }
            prescription.setPrescriptionImages(prescriptionImages);
        }

        prescriptionRepository.save(prescription);

        return "Prescription added successfully.";
    }














}
