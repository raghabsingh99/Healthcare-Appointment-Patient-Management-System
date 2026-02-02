package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@Builder
public class LabReport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long appointmentId;

    private String originalFileName;
    private String storedFileName;

    private String contentType;
    private long size;

    private Instant uploadAt = Instant.now();
}
