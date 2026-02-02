package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.LabReport;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.LabRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LabReportService {
    private final LabRepository labRepository;

    @Value("$app.files.labReportsDir}")
    private String dir;

    public LabReport upload(Long patientId,Long appointmentId, MultipartFile file) throws IOException {

        Files.createDirectories(Paths.get(dir));

        String stored = UUID.randomUUID()+"-"+file.getOriginalFilename();
        Path target = Paths.get(dir).resolve(stored);

        Files.copy(file.getInputStream(),target, StandardCopyOption.REPLACE_EXISTING);

        LabReport labReport = LabReport.builder()
                .patientId(patientId)
                .appointmentId(appointmentId)
                .originalFileName(file.getOriginalFilename())
                .storedFileName(stored)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();
        return labRepository.save(labReport);
    }

    public LabReport getMeta(Long id) throws NotFoundException {
        return labRepository.findById(id).orElseThrow(()-> new NotFoundException("LabReport not found"));

    }

    public Path getFilePath(Long id) throws NotFoundException {
        LabReport labReport = getMeta(id);
        return Paths.get(dir).resolve(labReport.getStoredFileName());
    }


}
