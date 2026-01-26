package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientResponse {
    private Long id;
    private String name;
    private int age;
    private String email;
    private String phone;
}
