package com.healthcare.Healthcare.Appointment.Patient.Management.System.HAPMS.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;

import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;


@Component
public class JwtUtil {

    private final Key key;
    private final long expirationMs;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expirationMs}") long expirationMs

    ){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    public String generateToken(String username){
        Date now = new Date();
        Date exp = new Date(now.getTime()+expirationMs);
        String jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .setSubject(username)
                .setId(jti)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return parse(token).getBody().getSubject();

    }

    public String extractJti(String token){
        return parse(token).getBody().getId();
    }
    public Date extractExpiration(String token){
        return parse(token).getBody().getExpiration();
    }

    public boolean isValid(String token){
        try{
            parse(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }




}
