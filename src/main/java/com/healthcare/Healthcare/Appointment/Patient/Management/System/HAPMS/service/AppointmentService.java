package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.AppointmentCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.AppointmentSearchRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.AppointmentResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.*;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.AppointmentRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    private final DoctorSlotService doctorSlotService;
    private final NotificationService notificationService;
    private final AuditLogService auditLogService;
    private final DoctorRepository doctorRepository;

    @Autowired
    private AppointmentSearchSpec appointmentSearchSpec;


    @Transactional
    public AppointmentResponse book(AppointmentCreateRequest req) throws NotFoundException, BusinessRuleException {
        Patient patient = patientService.getEntity(req.getPatientId());


        DoctorSlot slot = doctorSlotService.getAvailableSlot(req.getSlotId());
        Doctor doctor = slot.getDoctor();
        LocalDateTime appTime = slot.getStartTime();

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();


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
        if(appointmentRepository.existsOverlap(doctor.getId(), AppointmentStatus.BOOKED,dStart,dEnd)){
            throw new BusinessRuleException("Doctor already has an overlapping appointment");
        }

        Appointment saved = appointmentRepository.save(Appointment.builder()
                .appointmentDateTime(appTime)
                .status(AppointmentStatus.BOOKED)
                .patient(patient)
                .doctor(doctor)
                .build());

        notificationService.sendAppointmentBooked(patient.getEmail(),
                "Your appointment is booked" + saved.getAppointmentDateTime());
        doctorSlotService.markedBooked(slot);

        auditLogService.log(username,"BOOK_APPOINTMENT","APPOINTMENT_ID= "+saved.getId());
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

    public List<AppointmentResponse> getStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    public List<String> reportBookCountPerDoctor() {
        return appointmentRepository.findByStatus(AppointmentStatus.BOOKED).stream()
                .collect(Collectors.groupingBy(a -> a.getDoctor().getName(), Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(e -> e.getKey() + "=>" + e.getValue())
                .toList();
    }

    @Transactional
    public AppointmentResponse updateStatus(Long appointmentId, AppointmentStatus newStatus) throws NotFoundException, BusinessRuleException {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                () -> new NotFoundException("Appointment not found" + appointmentId));
        if (appointment.getStatus() != AppointmentStatus.BOOKED) {
            throw new BusinessRuleException("Only BOOKED appointments can be updated.Current: " + appointment.getStatus());
        }

        appointment.setStatus(newStatus);
        Appointment saved = appointmentRepository.save(appointment);
        return toResponse(saved);

    }
    public long assignDoctorAutomatically(){
        return doctorRepository.findDoctorsByLoad().get(0);
    }


    public List<AppointmentResponse> search(AppointmentSearchRequest req) {
        return appointmentRepository
                .findAll(appointmentSearchSpec.build(req))
                .stream()
                .map(this::toResponse)
                .toList();
    }


}





