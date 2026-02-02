package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.MedicalHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistoryEntry,Long> {
    List<MedicalHistoryEntry> findByPatientIdOrderByDateDesc(Long patientId);
}
