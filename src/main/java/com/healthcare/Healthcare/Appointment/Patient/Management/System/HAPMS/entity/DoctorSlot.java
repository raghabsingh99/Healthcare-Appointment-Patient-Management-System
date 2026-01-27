package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class DoctorSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTIme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status;



}
