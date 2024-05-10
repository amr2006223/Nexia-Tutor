package com.example.report_generation.report_generation.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.example.report_generation.report_generation.kafka.DyslexiaTypeProducer;
import com.example.report_generation.report_generation.models.DyslexiaCategory;
import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.repository.DyslexiaCategoryRepository;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DyslexiaCategoryServiceTest {

    @MockBean
    private DyslexiaCategoryRepository categoryRepository;

    @MockBean
    private DyslexiaTypeProducer dyslexiaTypeProducer;

    @MockBean
    private UserService userService;

    @InjectMocks
    private DyslexiaCategoryService dyslexiaCategoryService;

    @BeforeEach
    public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
public void categorizeUser_success() {
    // Arrange
    User user = new User();
    user.setId("testUser");
    
    UserData latestData = new UserData();
    Map<String, String> record = new HashMap<>();
    record.put("Accuracy1", "0.75");
    record.put("Accuracy2", "0.80");
    latestData.setRecord(record);
    
    when(userService.getLatestRecord(user)).thenReturn(latestData);
    
    DyslexiaCategory category1 = new DyslexiaCategory();
    category1.setName("Category1");
    category1.setStart(1);
    category1.setEnd(2);
    category1.setAverage(0.78);
    
    List<DyslexiaCategory> categories = new ArrayList<>();
    categories.add(category1);
    when(categoryRepository.findAll()).thenReturn(categories);
    
    // Act
    List<DyslexiaCategory> result = dyslexiaCategoryService.categorizeUser(user);
    
    // Assert
    assertEquals(1, result.size());
    assertEquals(category1, result.get(0));
    verify(dyslexiaTypeProducer, times(1)).broadcastDyslexiaType(result, "Categorizing User", user.getId());
}
@Test
public void categorizeUser_nullData() {
    // Arrange
    User user = new User();
    
    when(userService.getLatestRecord(user)).thenReturn(null);
    
    // Act
    List<DyslexiaCategory> result = dyslexiaCategoryService.categorizeUser(user);
    
    // Assert
    assertEquals(0, result.size());
    verifyNoInteractions(dyslexiaTypeProducer);
}

@Test
void saveCategory_success() {
    // Arrange
    DyslexiaCategory category = new DyslexiaCategory();
    category.setName("Category1");
    when(categoryRepository.save(category)).thenReturn(category);

    // Act
    DyslexiaCategory savedCategory = dyslexiaCategoryService.saveCategory(category);

    // Assert
    assertEquals(category, savedCategory);
    verify(categoryRepository, times(1)).save(category);
}

@Test
void getCategoryByName_success() {
    // Arrange
    String categoryName = "Category1";
    DyslexiaCategory category = new DyslexiaCategory();
    category.setName(categoryName);

    when(categoryRepository.findCategoryByName(categoryName)).thenReturn(category);

    // Act
    DyslexiaCategory foundCategory = dyslexiaCategoryService.getCategoryByName(categoryName);

    // Assert
    assertEquals(category, foundCategory);
    verify(categoryRepository, times(1)).findCategoryByName(categoryName);
}
    
}
