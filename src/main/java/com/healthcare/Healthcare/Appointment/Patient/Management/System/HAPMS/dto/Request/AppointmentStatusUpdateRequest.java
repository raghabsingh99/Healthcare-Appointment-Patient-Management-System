package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentStatusUpdateRequest {
    @NotNull
    private AppointmentStatus status;
}
