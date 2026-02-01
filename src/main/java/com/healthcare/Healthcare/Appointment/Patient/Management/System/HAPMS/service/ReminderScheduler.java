package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Appointment;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderScheduler {
    private final AppointmentRepository appointmentRepository;
    private final NotificationService notificationService;

    public void remindUpComing(){
        LocalDateTime now  = LocalDateTime.now();
        LocalDateTime from = now.plusHours(24).minusMinutes(2);
        LocalDateTime to = now.plusHours(24).plusMinutes(2);

        List<Appointment> appointments = appointmentRepository.findBookedBetween(from,to);

        for(Appointment appointment : appointments){
            String email = appointment.getPatient().getEmail();
            notificationService.sendAppointmentBooked(email,
                    "Reminder: You have an appointment at "+appointment.getAppointmentDateTime());
        }

        log.info("Reminder job checked {} appointments",appointments.size());
    }
}
