package com.example.report_generation.report_generation.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import com.example.report_generation.report_generation.models.DyslexiaCategory;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ScreeningServiceTest {

    @MockBean
    private DyslexiaCategoryService dyslexiaCategoryService;

    @MockBean
    private RestTemplate restTemplate;

    @InjectMocks
    private ScreeningService screeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     @Test
    void getAverageFromScreeningService_success() {
        // Arrange
        String responseBody = "[{\"name\":\"Category1\",\"average\":0.5},{\"name\":\"Category2\",\"average\":0.7}]";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(responseBody);
        when(restTemplate.getForEntity(any(String.class), eq(String.class)))
            .thenReturn(responseEntity);

        DyslexiaCategory category1 = new DyslexiaCategory();
        category1.setName("Category1");
        category1.setAverage(0.5);
        DyslexiaCategory category2 = new DyslexiaCategory();
        category2.setName("Category2");
        category2.setAverage(0.7);
        List<DyslexiaCategory> expectedCategories = Arrays.asList(category1, category2);
        when(dyslexiaCategoryService.getCategoryByName(anyString())).thenReturn(null);
        
        // Act
        List<DyslexiaCategory> actualCategories = screeningService.getAverageFromScreeningService();

        // Assert
        assertEquals(expectedCategories.size(), actualCategories.size());
        assertEquals(expectedCategories.get(0).getName(), actualCategories.get(0).getName());
        assertEquals(expectedCategories.get(1).getName(), actualCategories.get(1).getName());
    }
}
