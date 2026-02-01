package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.controller;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.LoginRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.LogoutRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.RefreshTokenRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.RegisterRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.AuthResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service.AuthService;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.util.SimpleRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public AuthResponse login(@Valid @RequestBody LoginRequest req, HttpServletRequest http) throws BusinessRuleException {
        String key = http.getRemoteAddr() + ":" + req.getUsername();
        if (!SimpleRateLimiter.allow(key)) {
            throw new BusinessRuleException("Too many login attempts. Please try later.");
        }
        return authService.login(req);
    }


    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid@RequestBody RefreshTokenRequest req) throws BusinessRuleException {
        return authService.refresh(req.getRefreshToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @Valid@RequestBody LogoutRequest req,
            @RequestHeader(value = "Authorization",required = false)String authHeader
            ) throws BusinessRuleException {
        authService.logout(req.getRefreshToken(),authHeader);
        return ResponseEntity.ok("Logged out");
    }
}
