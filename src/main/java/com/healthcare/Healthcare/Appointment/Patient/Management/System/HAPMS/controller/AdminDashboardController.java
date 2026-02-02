package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.AppointmentRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.DoctorRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/admin/dashboard")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> stats(){
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23,59,59);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("totalPatients", patientRepository.count());
        res.put("totalDoctors",doctorRepository.count());
        res.put("appointmentTodau",appointmentRepository.countToday(start,end));
        res.put("bookCount",appointmentRepository.countByStatus(AppointmentStatus.BOOKED));
        res.put("completedCount",appointmentRepository.countByStatus(AppointmentStatus.COMPLETED));
        res.put("cancelledCount",appointmentRepository.countByStatus(AppointmentStatus.CANCELED));

        List<Map<String,Object>> top = appointmentRepository.topDoctors().stream()
                .limit(5)
                .map(row->Map.of("doctorName",row[0],"appointments",row[1]))
                .toList();
        res.put("topDoctors",top);
        return res;
    }
}
