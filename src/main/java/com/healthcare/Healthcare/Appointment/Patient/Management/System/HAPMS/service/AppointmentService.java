package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.AppointmentCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.AppointmentResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Doctor;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Patient;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.EndDocument;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Transactional
    public AppointmentResponse book(AppointmentCreateRequest req) throws NotFoundException, BusinessRuleException {
        Patient patient = patientService.getEntity(req.getPatientId());
        Doctor doctor = doctorService.getEntity(req.getDoctorId());

        // Rule: Patient cannot have overlapping time
        LocalDateTime localDateTime = req.getAppointmentDateTime();
        LocalDateTime pStart = localDateTime.minusMinutes(30);
        LocalDateTime pEnd = localDateTime.plusMinutes(30);

        boolean patientConflict = !appointmentRepository.findByPatientIdAndAppointmentDateTimeBetween(
                        patient.getId(), pStart, pEnd)
                .isEmpty();
        if (patientConflict) {
            throw new BusinessRuleException("Patient already has a nearby appointment around: " + localDateTime);

        }
        // Doctor daily limit
        LocalDate day = localDateTime.toLocalDate();
        LocalDateTime dStart = day.atStartOfDay();
        LocalDateTime dEnd = day.atTime(23, 59, 59);
        long doctorCount = appointmentRepository.countByDoctorIdAndAppointmentDateTimeBetween(
                doctor.getId(), dStart, dEnd);
        if (doctor.getDailyLimit() > 0 && doctorCount >= doctor.getDailyLimit()) {
            throw new BusinessRuleException("Doctor reached daily appointment limit for " + day);
        }
        Appointment saved = appointmentRepository.save(Appointment.builder()
                .appointmentDateTime(localDateTime)
                .status(AppointmentStatus.BOOKED)
                .patient(patient)
                .doctor(doctor)
                .build());
        return toResponse(saved);
    }

    private AppointmentResponse toResponse(Appointment app) {
        return AppointmentResponse.builder()
                .id(app.getId())
                .appointmentTime(app.getAppointmentDateTime())
                .status(app.getStatus())
                .patientId(app.getPatient().getId())
                .patientName(app.getPatient().getName())
                .doctorId(app.getDoctor().getId())
                .doctorName(app.getDoctor().getName())
                .build();

    }

    public AppointmentResponse getById(Long id) throws NotFoundException {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Appointment not found: " + id));
        return toResponse(appointment);
    }

    public List<AppointmentResponse> getStatus(AppointmentStatus status){
        return appointmentRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    public List<String> reportBookCountPerDoctor(){
        return appointmentRepository.findByStatus(AppointmentStatus.BOOKED).stream()
                .collect(Collectors.groupingBy(a->a.getDoctor().getName(),Collectors.counting()))
        .entrySet().stream()
                .sorted((e1,e2)->Long.compare(e2.getValue(),e1.getValue()))
                .map(e->e.getKey()+ "=>" + e.getValue())
                .toList();
    }




}
