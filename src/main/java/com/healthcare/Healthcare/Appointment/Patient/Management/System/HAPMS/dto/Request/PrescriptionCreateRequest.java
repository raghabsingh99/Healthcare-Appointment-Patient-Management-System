package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrescriptionCreateRequest {
    @NotNull private Long appointmentId;
    @NotBlank private String notes;
    @NotBlank private String medications;
}
