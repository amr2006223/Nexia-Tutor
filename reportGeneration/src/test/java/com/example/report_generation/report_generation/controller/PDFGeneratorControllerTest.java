package com.example.report_generation.report_generation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.models.UserData;
import com.example.report_generation.report_generation.service.JwtService;
import com.example.report_generation.report_generation.service.PDFGeneratorService;
import com.example.report_generation.report_generation.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PDFGeneratorControllerTest {
    
    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private PDFGeneratorService pdfGeneratorService;

    @InjectMocks
    private PDFGeneratorController pdfGeneratorController;


    @BeforeEach
    public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
         MockitoAnnotations.openMocks(this);
    }
 @Test
    public void downloadPDF_failed() throws Exception {
        // Arrange
        String id = "testId";
        UserData userData = new UserData();
        List<UserData> data = new ArrayList<>();
        User user = new User(id, "testUserName", data);
        when(userService.getUserById(any(String.class), any(String.class))).thenReturn(null);
        when(userService.getLatestRecord(any(User.class))).thenReturn(userData);
        doNothing().when(pdfGeneratorService).downloadDocument(any(HttpServletResponse.class), any(User.class));
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        // Act
        ResponseEntity<?> response = pdfGeneratorController.downloadPDF(httpServletResponse, any(String.class));

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("User Not Found");
    }

    @Test
    public void downloadPDF_success() throws Exception {
        // Arrange
        String id = "testId";
        UserData userData = new UserData();
        userData.setDate(new Date());
        List<UserData> data = new ArrayList<>();
        User user = new User(id, "testUserName", data);


        when(jwtService.extractUUID(any(String.class))).thenReturn(id);
        when(userService.getUserById(any(String.class), any(String.class))).thenReturn(user);
        when(userService.getLatestRecord(any(User.class))).thenReturn(userData);
        doNothing().when(pdfGeneratorService).downloadDocument(any(HttpServletResponse.class), any(User.class));
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        // Act
        ResponseEntity<?> response = pdfGeneratorController.downloadPDF(httpServletResponse, id);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(null);
    }
    
    @Test
    public void generatePDF_success() throws Exception {
        // Arrange
        String token = "testToken";
        String id = "testId";
        User user = new User(id, "testUserName", new ArrayList<>());
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", token);

        when(jwtService.extractUUID(anyString())).thenReturn(id);
        when(userService.getUserById(anyString(), anyString())).thenReturn(user);
        when(pdfGeneratorService.generateDocumentInServer(any(User.class))).thenReturn(true);

        // Act
        ResponseEntity<String> response = pdfGeneratorController.generatePDF(requestBody);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Pdf Generated Successfully");
    }
    @Test
    public void generatePDF_failure() throws IOException {
        // Arrange
        String token = "testToken";
        String id = "testId";
        User user = new User(id, "testUserName", new ArrayList<>());
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", token);

        when(jwtService.extractUUID(anyString())).thenReturn(id);
        when(userService.getUserById(anyString(), anyString())).thenReturn(user);
        when(pdfGeneratorService.generateDocumentInServer(user)).thenReturn(false);

        // Act
        ResponseEntity<String> response = pdfGeneratorController.generatePDF(requestBody);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Failed to generate PDF");
    }
}
