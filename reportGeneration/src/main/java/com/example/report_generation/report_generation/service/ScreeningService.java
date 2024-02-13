package com.example.report_generation.report_generation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.repository.DyslexiaCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ScreeningService {
    private final RestTemplate restTemplate;
    private final DyslexiaCategoryRepository _categoryRepository;
    @Autowired
    UserService _userService;
    String filePath = "src\\main\\resources\\json\\ImportantUser.json";
    public ScreeningService(RestTemplate restTemplate, DyslexiaCategoryRepository categoryRepository) {
        this.restTemplate = restTemplate;
        _categoryRepository = categoryRepository;
    }

    public DyslexiaCategory getAverageFromScreeningService() {
        String url = "http://127.0.0.1:5000/average";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Process the response as needed
        String responseBody = response.getBody();
        System.out.println("Response: " + responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DyslexiaCategory category =  objectMapper.readValue(responseBody, DyslexiaCategory.class);
            saveCategory(category);
            return category;
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null;
        }

    }
    
    public User getUserWithAlphaAwarness(String userId) {
        String url = "http://127.0.0.1:5000/mockUser";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Process the response as needed
        String responseBody = response.getBody();
        System.out.println("Response: " + responseBody);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(responseBody, User.class);
            user.setId(userId);
           _userService.InsertUser(user,filePath);

            return user;
        } catch (Exception e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null;
        }

    }

    public String postSomethingAndGetResponse() {
        String url = "http://127.0.0.1:5000/average";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create your request object, if needed
        String requestBody = "{\"key\":\"value\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Perform the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity, // Pass your request entity here if needed
                String.class);

        // Process the response as needed
        String responseBody = response.getBody();
        System.out.println("Response: " + responseBody);

        return responseBody;
    }

    public DyslexiaCategory saveCategory(DyslexiaCategory dyslexiaCategory) {
        return _categoryRepository.save(dyslexiaCategory);
    }
    
    public DyslexiaCategory getCategoryByName(String categoryName) {
        return _categoryRepository.findCategoryByName(categoryName);
    }
}
