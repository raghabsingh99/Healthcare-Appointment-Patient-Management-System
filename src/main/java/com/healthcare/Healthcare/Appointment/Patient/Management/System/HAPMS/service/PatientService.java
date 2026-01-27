package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.PatientCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.PatientResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Patient;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponse create(PatientCreateRequest req){
        Patient patient = Patient.builder()
                .name(req.getName())
                .age(req.getAge())
                .email(req.getEmail())
                .phone(req.getPhone())
                .build();
        Patient saved = patientRepository.save(patient);
        return toResponse(saved);
    }

    @Cacheable(value = "patients",key = "#id")
    public Patient getEntity(Long id) throws NotFoundException {
        return patientRepository.findById(id).orElseThrow(
                ()->new NotFoundException("Patient not found" +id));
    }

    public List<PatientResponse> getAll(){
        return patientRepository.findAll().stream().map(this::toResponse).toList();
    }

    private PatientResponse toResponse(Patient p) {
        return PatientResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .age(p.getAge())
                .email(p.getEmail())
                .phone(p.getPhone())
                .build();
    }

    public Page<PatientResponse> getAllPaged(int page,int size,String sortBy,String dir){
        Sort sort = dir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return patientRepository.findAll(pageable).map(this::toResponse);
    }
}
