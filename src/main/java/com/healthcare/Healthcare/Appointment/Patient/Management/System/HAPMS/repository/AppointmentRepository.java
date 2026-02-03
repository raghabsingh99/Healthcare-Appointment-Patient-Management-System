package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long>, JpaSpecificationExecutor<Appointment> {

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

    long countByStatus(AppointmentStatus status);

    @Query("select count(a) from Appointment a where a.appointmentDateTime >= :start and a.appointmentDateTime < :end")

    long countToday(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
        select d.name, count(a.id)
        from Appointment a join a.doctor d
        group by d.name
        order by count(a.id) desc
        """)
    List<Object[]> topDoctors();

    @Query("""
select count(a) > 0
from Appointment a
where a.doctor.id = :doctorId
  and a.status = :status
  and :start < a.appointmentDateTime
  and a.appointmentDateTime < :end
""")
    boolean existsOverlap(
            @Param("doctorId") Long doctorId,
            @Param("status") AppointmentStatus status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );




    @Query("""
        select a.appointmentDateTime
        from Appointment a
        where a.doctor.id = :doctorId
          and a.status = AppointmentStatus.BOOKED
          and DATE(a.appointmentDateTime) = :date
    """)
    List<LocalDateTime> findBookedTimes(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date
    );


}
