package com.example.report_generation.report_generation.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ScreeningService {
    private static final String AVERAGE_URL = "http://localhost:8080/screening/average";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DyslexiaCategoryService dyslexiaCategoryService;

    public List<DyslexiaCategory> getAverageFromScreeningService() {
        try {
            // Make a GET request to fetch the average dyslexia categories
            ResponseEntity<String> response = restTemplate.getForEntity(AVERAGE_URL, String.class);
            String responseBody = response.getBody();
            System.out.println("Response: " + responseBody);

            // Parse the JSON response to a list of DyslexiaCategory objects
            List<DyslexiaCategory> categories = parseResponseToCategories(responseBody);
            updateDyslexiaCategories(categories);
            return categories;
        } catch (IOException e) {
            // Handle parsing errors and log the exception
            System.err.println("Failed to parse response: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // Handle any other exceptions and log the error
            System.err.println("Error fetching averages: " + e.getMessage());
            return null;
        }
    }

    private List<DyslexiaCategory> parseResponseToCategories(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<DyslexiaCategory>> typeReference = new TypeReference<List<DyslexiaCategory>>() {
        };
        return objectMapper.readValue(responseBody, typeReference);
    }

    private void updateDyslexiaCategories(List<DyslexiaCategory> categories) {
        for (DyslexiaCategory dyslexiaCategory : categories) {
            // Check if the category already exists in the database
            DyslexiaCategory existingCategory = dyslexiaCategoryService.getCategoryByName(dyslexiaCategory.getName());

            if (existingCategory == null) {
                // Save the new category if it doesn't exist
                dyslexiaCategoryService.saveCategory(dyslexiaCategory);
            } else {
                // Update the average value of the existing category and save
                existingCategory.setAverage(dyslexiaCategory.getAverage());
                dyslexiaCategoryService.saveCategory(existingCategory);
            }
        }
    }

}
