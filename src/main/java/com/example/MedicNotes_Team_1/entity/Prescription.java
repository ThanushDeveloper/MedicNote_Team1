package com.example.MedicNotes_Team_1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescription_id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patientId")
    private Patient patient;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Temporal(TemporalType.DATE)
    private Date prescription_date;

    @Temporal(TemporalType.DATE)
    private Date next_visit_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at = new Date();

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL)
    private List<Medicine> medicines;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL)
    private List<PrescriptionImage> prescriptionImages;

}
