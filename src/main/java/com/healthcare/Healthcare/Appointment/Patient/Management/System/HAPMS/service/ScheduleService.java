package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.DoctorWeeklyScheduleRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Doctor;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.DoctorSlot;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.SlotStatus;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.DoctorSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final DoctorService doctorService;
    private final DoctorSlotRepository doctorSlotRepository;

    public int generateWeeklySlots(DoctorWeeklyScheduleRequest req) throws BusinessRuleException, NotFoundException {
        if (!req.getEndTime().isAfter(req.getStartTime())) {
            throw new BusinessRuleException("end time must be after startTIme");
        }

        Doctor doctor = doctorService.getEntity(req.getDoctorId());
        LocalDate today = LocalDate.now();
        LocalDate endTime = today.plusWeeks(req.getWeeksAhead());

        List<DoctorSlot> toSave = new ArrayList<>();
        for (LocalDate dateTime = today; !dateTime.isAfter(endTime); dateTime = dateTime.plusDays(1)) {
            if (!req.getDays().contains(dateTime.getDayOfWeek())) continue;

            LocalDateTime start = LocalDateTime.of(dateTime, req.getStartTime());
            LocalDateTime end = LocalDateTime.of(dateTime, req.getEndTime());

            for (LocalDateTime time = start; time.plusMinutes(req.getSlotMinutes()).compareTo(end) <= 0;
                 time = time.plusMinutes(req.getSlotMinutes())) {
                DoctorSlot slot = DoctorSlot.builder()
                        .doctor(doctor)
                        .startTime(time)
                        .endTIme(time.plusMinutes(req.getSlotMinutes()))
                        .status(SlotStatus.AVAILABLE)
                        .build();
                toSave.add(slot);

            }


        }
        doctorSlotRepository.saveAll(toSave);
        return toSave.size();
    }
}
