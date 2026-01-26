package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    Optional<Prescription> findByAppointment_Id(Long appointmentId);
}
