package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.LoginRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Request.RegisterRequest;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.dto.Response.AuthResponse;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.RefreshToken;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.Role;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.User;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.UserRepository;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public void register(RegisterRequest req) throws BusinessRuleException {
        userRepository.findByUsername(req.getUsername()).ifPresent(u->{
            try {
                throw new BusinessRuleException("Username already exists: "+ req.getUsername());
            } catch (BusinessRuleException e) {
                throw new RuntimeException(e);
            }
        });

        Role role;
        try{
            role = Role.valueOf(req.getRole().toUpperCase());
        }catch (IllegalArgumentException e){
            throw new BusinessRuleException("Invalid role: " +req.getRole());
        }
        userRepository.save(User.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(role)
                .build());


    }

    public AuthResponse login(LoginRequest req) throws BusinessRuleException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        String accessToken = jwtUtil.generateToken(req.getUsername());
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(()->new BusinessRuleException("User not found"));

        String refreshToken = refreshTokenService.create(user).getToken();
        return new AuthResponse(accessToken,refreshToken);
    }

    public AuthResponse refresh(String refreshToken) throws BusinessRuleException {
        RefreshToken refreshToken1 = refreshTokenService.validation(refreshToken);
        String newAccess = jwtUtil.generateToken(refreshToken1.getUser().getUsername());

        return new AuthResponse(newAccess,refreshToken);
    }

    public void logout(String refreshToken,String accessToken) throws BusinessRuleException {
        refreshTokenService.revoke(refreshToken);

        if(accessToken != null && accessToken.startsWith("Bearer ")){
            String token = accessToken.substring(7);
            String jti = jwtUtil.extractJti(token);

            long ms = jwtUtil.extractExpiration(token).getTime() - System.currentTimeMillis();
            if (ms > 0) tokenBlacklistService.blacklist(jti, java.time.Duration.ofMillis(ms));
        }
    }

}
