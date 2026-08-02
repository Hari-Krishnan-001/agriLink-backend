package com.agriconnect.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {

    // Secret key (Change this in production)
    private static final String SECRET_KEY =
            "AgriConnectSecretKey12345678901234567890";

    // Token validity (24 hours)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    private SecretKey getSigningKey() {
        return new SecretKeySpec(
                SECRET_KEY.getBytes(),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

    // Generate JWT Token
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Validate token
    public boolean isTokenValid(String token, String email) {

        String extractedEmail = extractEmail(token);

        return extractedEmail.equals(email);
    }
}