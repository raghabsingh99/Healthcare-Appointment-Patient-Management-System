package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionResponse {
    private Long id;
    private Long appointmentId;
    private String notes;
    private String medications;
}
