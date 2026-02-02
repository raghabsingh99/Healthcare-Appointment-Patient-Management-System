package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.LabReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends JpaRepository<LabReport,Long> {

    List<LabReport> findByPatientId(Long patientId);
}
