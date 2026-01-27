package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.RefreshToken;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.entity.User;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.exception.BusinessRuleException;
import com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken create(User user){
        String token = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        Instant exp = Instant.now().plus(14, ChronoUnit.DAYS);

        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(token)
                        .user(user)
                        .expiresAt(exp)
                        .revoked(false)
                        .build());

    }

    public RefreshToken validation(String token) throws BusinessRuleException {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(()->new BusinessRuleException("Invalid refresh Token"));

        if(refreshToken.isRevoked()) throw new BusinessRuleException("Invalid refresh token");
        if(refreshToken.getExpiresAt().isBefore(Instant.now())) throw new BusinessRuleException("Refresh token expired");
        return refreshToken;

    }
    public void revoke(String token) throws BusinessRuleException {
        RefreshToken refreshToken = validation(token);
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

    }
}
