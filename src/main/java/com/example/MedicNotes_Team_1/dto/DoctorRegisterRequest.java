package com.example.MedicNotes_Team_1.dto;

import com.example.MedicNotes_Team_1.entity.Doctor;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegisterRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private String specialization;
    private Doctor.Gender gender;
    private Date dob;
    private byte[] doctor_image; // send as Base64 in JSON if needed
    private Doctor.Status status;
}
