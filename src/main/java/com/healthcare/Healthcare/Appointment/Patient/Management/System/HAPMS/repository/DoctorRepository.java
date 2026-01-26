package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
