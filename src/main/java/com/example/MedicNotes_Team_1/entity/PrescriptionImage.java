package com.example.MedicNotes_Team_1.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PrescriptionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
