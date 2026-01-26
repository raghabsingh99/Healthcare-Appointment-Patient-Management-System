package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.AppointmentCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.AppointmentResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AppointmentResponse book(@Valid @RequestBody AppointmentCreateRequest req) throws BusinessRuleException, NotFoundException {
        return appointmentService.book(req);
    }

    @GetMapping("/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<AppointmentResponse> byStatus(@PathVariable AppointmentStatus status){
        return appointmentService.getStatus(status);
    }

    @GetMapping("/reports/bookedperdoctor")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<String> reportBookedPerDoctor(){
        return appointmentService.reportBookCountPerDoctor();

    }
}
