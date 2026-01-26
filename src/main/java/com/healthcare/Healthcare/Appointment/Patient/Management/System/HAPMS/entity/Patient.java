package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Scanner;
import java.util.Stack;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String phone;


}
