package com.nexia.nexia.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class jwtService {
    private String jwtSecret;// used for encoding
    private Algorithm algorithm;// algorithm for encoding
    private long validity;// expiration date

    public jwtService() {
        this.jwtSecret = "THISISTHESECRET";
        this.algorithm = Algorithm.HMAC256(this.jwtSecret);
        this.validity = 365 * 24 * 60 * 60 * 1000; // day*hr*min*sec*milisec
    }

    // encode uuid generate token
    public String generateToken(String uuid) {
        String token = JWT.create()
                .withSubject(uuid)
                .withExpiresAt(new Date(System.currentTimeMillis() + this.validity))
                .sign(this.algorithm);
        return token;
    }

    // decode token => extract uuid
    public String extractUUID(String token) {
        JWTVerifier jwtVerifier = JWT.require(this.algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();

    }
}
