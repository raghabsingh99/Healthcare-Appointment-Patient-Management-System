package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.PatientResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.PatientRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportExportController {
    private final PatientService patientService;


    @GetMapping("/export/patients.csv")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportPatientsCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename=patients.csv");

        try(PrintWriter writer = response.getWriter()){
            writer.println("id,name,age,email,phone");

            for(PatientResponse p : patientService.getAll()){
                writer.printf("%d,%s,%d,%s,%s%n",
                        p.getId(), safe(p.getName()), p.getAge(), safe(p.getEmail()), safe(p.getPhone()));
            }

            }
        }

    private String safe(String s) {
        if(s==null) return "";
        return s.replace(",","");
    }


}
