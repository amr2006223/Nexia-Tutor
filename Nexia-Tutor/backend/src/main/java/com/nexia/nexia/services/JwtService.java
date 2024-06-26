package com.nexia.nexia.services;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



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
        
        // Return the UUID from the response body
        return response.getBody();
    }
    
}
