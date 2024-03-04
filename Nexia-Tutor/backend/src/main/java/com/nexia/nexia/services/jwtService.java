package com.nexia.nexia.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nexia.nexia.services.iservices.IJwtService;

@Service
public class jwtService implements IJwtService {

    private final String jwtSecret = "w6d7mK6k7qdemci4ouZAzFKDWTHYq213"; // used for encoding
    private final Algorithm algorithm = Algorithm.HMAC256(this.jwtSecret); // algorithm for encoding
    private final long validity = 7776000000L; // expiration date in milliseconds
    
    @Override
    public String generateToken(String uuid) {
        Date expiresAt = new Date(System.currentTimeMillis() + this.validity);
        return JWT.create()
                .withSubject(uuid)
                .withExpiresAt(expiresAt)
                .sign(this.algorithm);
    }

    @Override
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

    @Override
    public String extractUUID(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(this.algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }
}
