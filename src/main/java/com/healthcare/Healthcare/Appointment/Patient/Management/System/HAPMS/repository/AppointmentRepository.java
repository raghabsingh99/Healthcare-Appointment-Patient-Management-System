package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(Long doctorId, LocalDateTime start,LocalDateTime end);
    List<Appointment> findByPatientIdAndAppointmentDateTimeBetween(Long patientId,LocalDateTime start,LocalDateTime end);

    long countByDoctorIdAndAppointmentDateTimeBetween(Long doctorId,LocalDateTime start,LocalDateTime end);
    List<Appointment> findByStatus(AppointmentStatus status);

    @Query("""
select a from Appointment a
where a.status = AppointmentStatus.BOOKED
and a.appointmentDateTime between :from and :to
""")
    List<Appointment> findBookedBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

}
