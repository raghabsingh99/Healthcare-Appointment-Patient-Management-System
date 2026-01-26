package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank private String username;
    @NotBlank private String password;
}
