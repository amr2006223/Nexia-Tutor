package com.nexia.nexia.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

    private final String jwtSecret; // used for encoding
    private final Algorithm algorithm; // algorithm for encoding
    private final long validity; // expiration date in milliseconds

    public JwtService(@Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.validity:7776000000}") long validity) {
        this.jwtSecret = jwtSecret;
        this.algorithm = Algorithm.HMAC256(this.jwtSecret);
        this.validity = validity;
    }

    // encode uuid generate token
    public String generateToken(String uuid) {
        Date expiresAt = new Date(System.currentTimeMillis() + this.validity);
        return JWT.create()
                .withSubject(uuid)
                .withExpiresAt(expiresAt)
                .sign(this.algorithm);
    }

    // Check if token is expired
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

    // decode token => extract uuid
    public String extractUUID(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(this.algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }
}
