package com.example.report_generation.report_generation.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void extractUUID_success() {
        // Arrange
        String token = "validToken";
        String expectedUUID = "uuid123";
        String responseBody = expectedUUID;
        String microserviceUrl = "http://localhost:9898/identity-service/auth/token/getPayload";

       when(restTemplate.postForEntity(
        eq(microserviceUrl),
        any(HttpEntity.class),
        eq(String.class)
        )).thenReturn(ResponseEntity.ok(responseBody));
        // Act
        String actualUUID = jwtService.extractUUID(token);

        // Assert
        assertEquals(expectedUUID, actualUUID);
        verify(restTemplate, times(1)).postForEntity(eq(microserviceUrl), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void validate_success() throws IOException {
        // Arrange
        String token = "validToken";
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("key", "value");
        String responseBody = "{\"key\":\"value\"}";
        String microserviceUrl = "http://localhost:9898/identity-service/auth/validate";

        when(restTemplate.postForEntity(
                eq(microserviceUrl),
                any(HttpEntity.class),
                eq(String.class))).thenReturn(ResponseEntity.ok(responseBody));

        // Act
        Map<String, String> actualMap = jwtService.validate(token);

        // Assert
        assertEquals(expectedMap, actualMap);
        verify(restTemplate, times(1)).postForEntity(eq(microserviceUrl), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void validate_errorInParsing() throws IOException {
        // Arrange
        String token = "validToken";
        String invalidResponse = "{ invalid json }";
        String microserviceUrl = "http://localhost:9898/identity-service/auth/validate";

        when(restTemplate.postForEntity(
                eq(microserviceUrl),
                any(HttpEntity.class),
                eq(String.class))).thenReturn(ResponseEntity.ok(invalidResponse));

        // Act
        Map<String, String> actualMap = jwtService.validate(token);

        // Assert
        assertTrue(actualMap.containsKey("error"));
        verify(restTemplate, times(1)).postForEntity(eq(microserviceUrl), any(HttpEntity.class), eq(String.class));
    }
}