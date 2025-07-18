package com.example.MedicNotes_Team_1.service;
// package: com.example.MedicNotes_Team_1.service


import com.example.MedicNotes_Team_1.entity.Patient;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface PatientService {

    String addPatient(Patient patient, MultipartFile image);

    List<Patient> getAllPatients();

    List<Patient> getPatientsRegisteredToday();

    List<Patient> getPatientsRegisteredYesterday();

    List<Patient> getPatientsRegisteredThisWeek();

    List<Patient> getPatientsRegisteredThisMonth();

    List<Patient> getPatientsByGender(Patient.Gender gender);

    List<Patient> getPatientsByName(String name);

    List<Patient> getPatientsByPhone(String phone);

    List<Patient> getPatientsRegisteredOnDate(Date date);

    List<Patient> getPatientsByTreatment(String treatment);

    String updatePatient(Long id, Patient updatedPatient, MultipartFile image);

    void deletePatient(Long id);
}
