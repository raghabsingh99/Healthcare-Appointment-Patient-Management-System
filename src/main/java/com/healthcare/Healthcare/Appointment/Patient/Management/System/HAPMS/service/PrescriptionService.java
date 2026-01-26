package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.PrescriptionCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.PrescriptionResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Prescription;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.AppointmentRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.prefs.BackingStoreException;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public PrescriptionResponse create(PrescriptionCreateRequest req) throws NotFoundException, BackingStoreException {
        Appointment appointment = appointmentRepository.findById(req.getAppointmentId()).orElseThrow(
                () -> new NotFoundException("Appointment not found" + req.getAppointmentId()));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new BackingStoreException("Prescription allowed only for COMPLETED appointment");
        }


        prescriptionRepository.findByAppointment_Id(appointment.getId()).ifPresent(p -> {
            try {
                throw new BusinessRuleException("Prescription already exists for appointment: " + appointment.getId());
            } catch (BusinessRuleException e) {
                throw new RuntimeException(e);
            }
        });

        Prescription saved = prescriptionRepository.save(Prescription.builder()
                .appointment(appointment)
                .notes(req.getNotes())
                .medications(req.getMedications())
                .build());
        return toResponse(saved);
    }

    private PrescriptionResponse toResponse(Prescription saved) {
        return PrescriptionResponse.builder()
                .id(saved.getId())
                .appointmentId(saved.getAppointment().getId())
                .notes(saved.getNotes())
                .medications(saved.getMedications())
                .build();
    }


}
