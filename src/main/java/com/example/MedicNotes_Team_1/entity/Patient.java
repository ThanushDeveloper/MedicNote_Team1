// package: com.example.MedicNotes_Team_1.entity

package com.example.MedicNotes_Team_1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String name;
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @JsonIgnore
    private String password;

    @Lob
    private byte[] patientImage;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date updatedAt = new Date();

    private String treatment;

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
