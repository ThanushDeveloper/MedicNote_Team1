package com.example.MedicNotes_Team_1.service;
// package: com.example.MedicNotes_Team_1.service


import com.example.MedicNotes_Team_1.entity.Patient;
import com.example.MedicNotes_Team_1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addPatient(Patient patient, MultipartFile image) {
        try {
            if (!patientRepository.findByPhone(patient.getPhone()).isEmpty()) {
                return "This phone " + patient.getPhone() + " is already used in another account";
            }

            if (image != null && !image.isEmpty()) {
                patient.setPatientImage(image.getBytes());
            }

            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            patient.setCreatedAt(new Date());
            patient.setUpdatedAt(new Date());

            patientRepository.save(patient);
            return "Patient added successfully.";
        } catch (IOException e) {
            return "Error saving patient image: " + e.getMessage();
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()) {
            throw new RuntimeException("No patient found");
        }
        return patients;
    }

    @Override
    public List<Patient> getPatientsRegisteredToday() {
        Calendar calendar = Calendar.getInstance();
        setDayRange(calendar, 0);
        return getPatientsBetween(calendar);
    }

    @Override
    public List<Patient> getPatientsRegisteredYesterday() {
        Calendar calendar = Calendar.getInstance();
        setDayRange(calendar, -1);
        return getPatientsBetween(calendar);
    }

    @Override
    public List<Patient> getPatientsRegisteredThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfWeek = calendar.getTime();

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfWeek = calendar.getTime();

        return patientRepository.findByCreatedAtBetween(startOfWeek, endOfWeek);

    }

    @Override
    public List<Patient> getPatientsRegisteredThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfMonth = calendar.getTime();

        return  patientRepository.findByCreatedAtBetween(startOfMonth, endOfMonth);
    }

    @Override
    public List<Patient> getPatientsByGender(Patient.Gender gender) {
        return patientRepository.findByGender(gender);
    }

    @Override
    public List<Patient> getPatientsByName(String name) {
        return patientRepository.findByName(name);
    }

    @Override
    public List<Patient> getPatientsByPhone(String phone) {
        return patientRepository.findByPhone(phone);
    }

    @Override
    public List<Patient> getPatientsRegisteredOnDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        setDayRange(calendar, 0);
        return getPatientsBetween(calendar);
    }

    @Override
    public List<Patient> getPatientsByTreatment(String treatment) {
        return patientRepository.findByTreatment(treatment);
    }

    @Override
    public String updatePatient(Long id, Patient updatedPatient, MultipartFile image) {
        try {
            Patient existingPatient = patientRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Patient with id " + id + " not found"));

            if (!existingPatient.getPhone().equals(updatedPatient.getPhone()) &&
                    !patientRepository.findByPhone(updatedPatient.getPhone()).isEmpty()) {
                return "Phone number already exists in another account";
            }

            existingPatient.setName(updatedPatient.getName());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setPhone(updatedPatient.getPhone());
            existingPatient.setAddress(updatedPatient.getAddress());
            existingPatient.setDob(updatedPatient.getDob());
            existingPatient.setGender(updatedPatient.getGender());
            existingPatient.setTreatment(updatedPatient.getTreatment());
            existingPatient.setStatus(updatedPatient.getStatus());
            existingPatient.setUpdatedAt(new Date());

            if (updatedPatient.getPassword() != null && !updatedPatient.getPassword().isEmpty()) {
                existingPatient.setPassword(passwordEncoder.encode(updatedPatient.getPassword()));
            }

            if (image != null && !image.isEmpty()) {
                existingPatient.setPatientImage(image.getBytes());
            }

            patientRepository.save(existingPatient);
            return "Patient updated successfully.";
        } catch (IOException e) {
            return "Error updating patient image: " + e.getMessage();
        }
    }

    @Override
    public void deletePatient(Long id) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient with id " + id + " not found"));
        patientRepository.delete(existingPatient);
    }

    private void setDayRange(Calendar calendar, int offset) {
        calendar.add(Calendar.DATE, offset);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private List<Patient> getPatientsBetween(Calendar calendar) {
        Date startOfDay = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        return patientRepository.findByCreatedAtBetween(startOfDay, endOfDay);

    }
}
