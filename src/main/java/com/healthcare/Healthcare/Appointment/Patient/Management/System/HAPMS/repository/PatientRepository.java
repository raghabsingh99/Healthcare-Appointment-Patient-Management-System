package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
}
