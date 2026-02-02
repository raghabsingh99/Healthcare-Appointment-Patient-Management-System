package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.LabReport;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.NotFoundException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.LabRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.LabReportService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/lab-reports")
@RequiredArgsConstructor
public class LabReportController {

    private final LabReportService labReportService;
    private final LabRepository repository;

    // ✅ Upload lab report
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public LabReport upload(
            @RequestParam Long patientId,
            @RequestParam(required = false) Long appointmentId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return labReportService.upload(patientId, appointmentId, file);
    }

    // ✅ List reports by patient
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public List<LabReport> list(@PathVariable Long patientId) {
        return repository.findByPatientId(patientId);
    }

    // ✅ Download report
    @GetMapping("/{id}/download")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long id)
            throws NotFoundException {

        Path path = labReportService.getFilePath(id);
        LabReport labReport = labReportService.getMeta(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(labReport.getContentType()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + labReport.getOriginalFileName() + "\""
                )
                .body(new FileSystemResource(path));
    }
}
