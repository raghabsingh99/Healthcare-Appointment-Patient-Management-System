package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.DoctorSlot;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Long> {


    @Query("""
select ds
from DoctorSlot ds
where ds.doctor.id = :doctorId
  and ds.status = :status
  and ds.startTime >= :start
  and ds.startTime < :end
""")
    List<DoctorSlot> findSlotsForDoctorOnDate(
            Long doctorId,
            SlotStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

}
