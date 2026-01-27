package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.DoctorSlotCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.DoctorSlot;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.DoctorSlotService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/slots")
public class SlotController {
    @Autowired
    private DoctorSlotService doctorSlotService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public DoctorSlot create(@Valid@RequestBody DoctorSlotCreateRequest request) throws BusinessRuleException, NotFoundException {
        return doctorSlotService.create(request);
    }

    @GetMapping("/doctor/{doctorId}/available")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public List<DoctorSlot> available(@PathVariable Long doctorId){
        return doctorSlotService.getAvailableSlots(doctorId);
    }

    @GetMapping("/{slotId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public DoctorSlot getOne(@PathVariable Long slotId) throws BusinessRuleException, NotFoundException {
        return doctorSlotService.getAvailableSlot(slotId);
    }




}
