package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final StringRedisTemplate redis;

    private String key(String jti){
        return "blacklist:jti:"+jti;
    }

    public void blacklist(String jti, Duration ttl){
        redis.opsForValue().set(key(jti),"1",ttl);
    }

    public boolean isBlacklisted(String jti){
        return redis.hasKey(key(jti));
    }
}
