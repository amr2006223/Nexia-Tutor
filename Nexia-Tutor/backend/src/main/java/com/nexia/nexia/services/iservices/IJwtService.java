package com.nexia.nexia.services.iservices;

import com.auth0.jwt.exceptions.JWTVerificationException;

public interface IJwtService {
    String extractUUID(String token) throws JWTVerificationException;
}
