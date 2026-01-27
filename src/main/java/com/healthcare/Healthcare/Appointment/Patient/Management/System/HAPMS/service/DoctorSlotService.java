package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.DoctorSlotCreateRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Doctor;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.DoctorSlot;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.SlotStatus;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.DoctorSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.nio.channels.NotYetBoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorSlotService {

    private final DoctorSlotRepository doctorSlotRepository;
    private final DoctorService doctorService;

    public DoctorSlot create(DoctorSlotCreateRequest req) throws BusinessRuleException, NotFoundException {
        if(!req.getEndTime().isAfter(req.getStartTime())){
            throw new BusinessRuleException("end time must be after start time");
        }

        Doctor doctor = doctorService.getEntity(req.getDoctorId());

        return doctorSlotRepository.save(DoctorSlot.builder()
                .doctor(doctor)
                .startTime(req.getStartTime())
                .endTIme(req.getEndTime())
                .status(SlotStatus.AVAILABLE)
                .build());
    }

    public DoctorSlot getAvailableSlot(Long slotId) throws NotFoundException, BusinessRuleException {
        DoctorSlot slot = doctorSlotRepository.findById(slotId)
                .orElseThrow(()-> new NotFoundException("Slot not found" +slotId));
        if(slot.getStatus() != SlotStatus.AVAILABLE){
            throw new BusinessRuleException("Slot is not available");
        }
        return slot;
    }

    public List<DoctorSlot> getAvailableSlots(Long doctorId){
        return doctorSlotRepository.findByDoctorIdAndStatus(doctorId,SlotStatus.AVAILABLE);
    }

    public void markedBooked(DoctorSlot slot){
        slot.setStatus(SlotStatus.BOOKED);
        doctorSlotRepository.save(slot);
    }

}
