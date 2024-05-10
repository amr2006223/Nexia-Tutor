package com.example.report_generation.report_generation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Autowired
    private RestTemplate restTemplate;

    public String extractUUID(String token) {
        // Define the URL of the microservice endpoint
        String microserviceUrl = "http://localhost:9898/identity-service/auth/token/getPayload";

        // Create the request headers and set the content type to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        Map<String, String> requestBody = Map.of("token", token);

        // Create an HttpEntity with the request body and headers
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send a POST request and retrieve the response
        ResponseEntity<String> response = restTemplate.postForEntity(microserviceUrl, requestEntity, String.class);
        // Check if the response is null
        if (response == null) {
            throw new RuntimeException("Token is null"); // Handle the null case appropriately in your code
        }
        // Return the UUID from the response body
        return response.getBody();
    }

    public Map<String, String> validate(String token) {
        // Define the URL of the microservice endpoint
        String microserviceUrl = "http://localhost:9898/identity-service/auth/validate";

        // Create the request headers and set the content type to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        Map<String, String> requestBody = Map.of("token", token);

        // Create an HttpEntity with the request body and headers
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send a POST request and retrieve the response
        ResponseEntity<String> response = restTemplate.postForEntity(microserviceUrl, requestEntity, String.class);
        
        //convert String result to an json map
        TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {
        };

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap;

        try {
            responseMap = objectMapper.readValue(response.getBody(), typeRef);
            return responseMap;
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return error;
        }
    }
}
