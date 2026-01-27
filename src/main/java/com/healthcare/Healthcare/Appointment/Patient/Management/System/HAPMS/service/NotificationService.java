package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
    @Async
    public void sendAppointmentBooked(String toEmail,String message){
        log.info("Notification to {} => {}", toEmail, message);
    }
}
