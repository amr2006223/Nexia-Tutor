package com.example.basedomain.basedomain.Shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class jwtService {
    private final String jwtSecret;
    private final Algorithm algorithm;
    private final String validity;

    public jwtService(String _jwtSecret,
            String _validity) {
        this.jwtSecret = _jwtSecret;
        this.algorithm = Algorithm.HMAC256(jwtSecret);
        this.validity = _validity;
    }

    public String generateToken(String uuid) {
        Date expiresAt = new Date(System.currentTimeMillis() + Long.parseLong(this.validity));
        return JWT.create()
                .withSubject(uuid)
                .withExpiresAt(expiresAt)
                .sign(this.algorithm);
    }

    public boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Date expiresAt = decodedJWT.getExpiresAt();
            return expiresAt != null && expiresAt.before(new Date());
        } catch (JWTDecodeException exception) {
            // Invalid token format or unable to decode
            return true; // Treat as expired
        }
    }

    public String extractUUID(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(this.algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }
}
