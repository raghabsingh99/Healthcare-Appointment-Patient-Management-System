package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime start,LocalDateTime end);
    List<Appointment> findByPatientIdAndAppointmentDateTimeBetween(Long patientId,LocalDateTime start,LocalDateTime end);

    long countByDoctorIdAndAppointmentDateTimeBetween(Long doctorId,LocalDateTime start,LocalDateTime end);
    List<Appointment> findByStatus(AppointmentStatus status);

}
