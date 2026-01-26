package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.LoginRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.RegisterRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.AuthResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) throws BusinessRuleException {
        System.out.println("🔥 REGISTER ENDPOINT HIT");
        authService.register(req);
        return ResponseEntity.ok("User registered successfully");
    }



    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req){
        return authService.login(req);
    }
}
