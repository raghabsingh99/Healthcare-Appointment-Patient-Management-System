package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
public class DoctorWeeklyScheduleRequest {

    @NotNull private long doctorId;

    @NotEmpty
    private Set<DayOfWeek> days;

    @NotNull private LocalTime startTime;
    @NotNull private LocalTime endTime;

    @Min(5)@Max(180)
    private int slotMinutes = 30;

    @Min(1)@Max(24)
    private int weeksAhead = 4;
}
