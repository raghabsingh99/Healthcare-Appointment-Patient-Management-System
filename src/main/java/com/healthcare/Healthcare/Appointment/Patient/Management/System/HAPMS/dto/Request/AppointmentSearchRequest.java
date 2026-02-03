package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class AppointmentSearchRequest {
    @NotNull
    private Long patientId;
    @NotNull private Long doctorId;
    @NotNull private LocalDateTime from;
    @NotNull private LocalDateTime to;
    private AppointmentStatus status;
}
