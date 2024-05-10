package com.example.identityservice.services;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @MockBean
    private JWTVerifier jwtVerifier;


    private JwtService jwtService =  new JwtService("secret", "3600000");;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void generateToken_validUUID() {
        // Arrange
        String uuid = "testUUID";
        jwtService = new JwtService("secret", "3600000"); // validity is 1 hour

        // Act
        String token = jwtService.generateToken(uuid);

        // Assert
        assertThat(token).isNotNull();
        DecodedJWT decodedJWT = JWT.decode(token);
        assertThat(decodedJWT.getSubject()).isEqualTo(uuid);
        assertThat(decodedJWT.getExpiresAt()).isAfter(new Date());
    }


    @Test
    public void extractUUID_validToken() throws Exception {
        // Arrange
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0VVVJRCIsImlhdCI6MTUxNjIzOTAyMn0.YfqI_q-SfQKRtiLKNl70VslR2lBGNikD0pq5msub2nU";
        String expectedUUID = "testUUID";

        // Act
        String result = jwtService.extractUUID(token);

        // Assert
        assertThat(result).isEqualTo(expectedUUID);
    }
}
