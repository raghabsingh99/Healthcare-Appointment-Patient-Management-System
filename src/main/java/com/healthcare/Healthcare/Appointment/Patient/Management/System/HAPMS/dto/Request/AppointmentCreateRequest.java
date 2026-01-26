package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCreateRequest {
    @NotNull private Long patientId;
    @NotNull private Long doctorId;
    @NotNull private LocalDateTime appointmentDateTime;

}
