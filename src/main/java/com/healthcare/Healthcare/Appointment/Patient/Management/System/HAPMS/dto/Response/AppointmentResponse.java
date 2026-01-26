package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
}
