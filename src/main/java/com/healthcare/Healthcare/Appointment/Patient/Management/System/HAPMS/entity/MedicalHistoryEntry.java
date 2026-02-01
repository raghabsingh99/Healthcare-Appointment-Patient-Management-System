package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
public class MedicalHistoryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate localDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;

    private String diagnosis;

    @Column(length = 200)
    private String notes;
}
