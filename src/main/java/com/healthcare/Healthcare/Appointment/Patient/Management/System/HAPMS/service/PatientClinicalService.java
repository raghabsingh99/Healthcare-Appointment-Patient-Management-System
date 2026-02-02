package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Allergy;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.MedicalHistoryEntry;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Patient;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.AllergyRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.MedicalHistoryRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.prefs.BackingStoreException;

@Service
@RequiredArgsConstructor
public class PatientClinicalService {

    private final AllergyRepository allergyRepository;
    private final PatientRepository patientRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;

    private Patient getPatientOrThrow(Long patientId) throws NotFoundException {
        return patientRepository.findById(patientId).orElseThrow(
        ()-> new NotFoundException("Patient not found "+patientId));

    }

    public List<Allergy> getAllAllergies(Long patientId){
        return allergyRepository.findByPatientId(patientId);
    }

    public Allergy addAllergy(Long patientId,String name) throws NotFoundException {
       Patient patient = getPatientOrThrow(patientId);

       Allergy allergy = Allergy.builder()
               .patient(patient)
               .name(name)
               .build();
       return allergyRepository.save(allergy);
    }

    public List<MedicalHistoryEntry> getHistory(Long patientId){
        return medicalHistoryRepository.findByPatientIdOrderByDateDesc(patientId);
    }

    public MedicalHistoryEntry addHistory(Long patientId,String diagnosis,String notes) throws NotFoundException {
        Patient patient = getPatientOrThrow(patientId);

        MedicalHistoryEntry entry = MedicalHistoryEntry.builder()
                .patient(patient)
                .entryDate(LocalDate.now())
                .diagnosis(diagnosis)
                .notes(notes)
                .build();
        return medicalHistoryRepository.save(entry);
    }
}
