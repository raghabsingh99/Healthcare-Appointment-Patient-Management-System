package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Allergy;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.MedicalHistoryEntry;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.PatientClinicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient/clinical")
@RequiredArgsConstructor
public class PatientClinicalController {
    private final PatientClinicalService patientClinicalService;

    @GetMapping("{patientId}/allergies")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<Allergy> allergies(@PathVariable Long patientId){
        return patientClinicalService.getAllAllergies(patientId);
    }
    @PostMapping("/{patientId}/allergies")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Allergy addAllergy(@PathVariable Long patientId,@RequestParam String name) throws NotFoundException {
        return patientClinicalService.addAllergy(patientId,name);
    }

    @GetMapping("/{patientId}/history")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<MedicalHistoryEntry> historyEntries(@PathVariable Long patientId){
        return patientClinicalService.getHistory(patientId);
    }

    @PostMapping("/{patientId}/history")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public MedicalHistoryEntry addHistory(
            @PathVariable Long patientId,
            @RequestParam String diagnosis,
            @RequestParam(required = false) String notes) throws NotFoundException {
        return patientClinicalService.addHistory(patientId,diagnosis,notes);

    }







}
