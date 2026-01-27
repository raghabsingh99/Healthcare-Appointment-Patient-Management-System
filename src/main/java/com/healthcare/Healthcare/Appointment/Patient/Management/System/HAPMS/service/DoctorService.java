package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Doctor;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Cacheable(value = "doctors",key = "#id")
    public Doctor getEntity(Long id) throws NotFoundException {
        return doctorRepository.findById(id).orElseThrow(()->new NotFoundException("Doctor not found" +id));
    }
}
