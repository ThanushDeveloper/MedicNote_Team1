package com.example.MedicNotes_Team_1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicine_id;

    private String name;
    private String dosage;
    private String frequency;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
