package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientCreateRequest {
    @NotBlank private String name;
    @Min(0)@Max(130) private int age;
    @Email private String email;
    private String phone;
}
