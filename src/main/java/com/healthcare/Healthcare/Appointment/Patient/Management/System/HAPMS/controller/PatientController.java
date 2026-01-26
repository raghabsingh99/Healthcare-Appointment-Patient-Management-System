package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.PatientCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.PatientResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<PatientResponse> getALL(){
        return patientService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PatientResponse create(@Valid @RequestBody PatientCreateRequest req){
        return patientService.create(req);
    }
}
