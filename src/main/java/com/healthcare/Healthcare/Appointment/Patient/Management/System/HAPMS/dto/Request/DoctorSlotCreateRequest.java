package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoctorSlotCreateRequest {
    @NotNull private Long doctorId;
    @NotNull private LocalDateTime startTime;
    @NotNull private LocalDateTime endTime;
}
