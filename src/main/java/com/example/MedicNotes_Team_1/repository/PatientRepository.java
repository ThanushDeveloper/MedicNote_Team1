// package: com.example.MedicNotes_Team_1.repository

package com.example.MedicNotes_Team_1.repository;

import com.example.MedicNotes_Team_1.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByCreatedAtBetween(Date start, Date end);


    List<Patient> findByGender(Patient.Gender gender);

    List<Patient> findByName(String name);

    List<Patient> findByPhone(String phone);

    List<Patient> findByTreatment(String treatment);

    Optional<Patient> findByEmail(String email);
}
