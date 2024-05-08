package com.example.identityservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.basedomain.basedomain.dto.UserDTO;
import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.services.AuthService;
import com.example.identityservice.services.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthService authService;

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_success() throws Exception {
        // Create a UserDTO object for the request body
        Date date = new Date();
        UserDTO userDTO = new UserDTO("testId", "testuser", "testpassword", date, "Egyptian", false, "null", "1234",
                "USER");
        // Mock the authService.register method to return the userDTO as it is
        when(authService.register(any(UserDTO.class))).thenReturn(userDTO);

        // Convert the userDTO object to JSON
        String jsonRequest = objectMapper.writeValueAsString(userDTO);

        // Perform a POST request to the /register endpoint
        mockMvc.perform(post("/identity-service/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.password").value("testpassword"))
                .andExpect(jsonPath("$.gender").value(false))
                .andExpect(jsonPath("$.birthDate").value(date))
                .andExpect(jsonPath("$.nationality").value("Egyptian"))
                .andExpect(jsonPath("$.parentPin").value("1234"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.token").value("null"));

        // Verify that the register method was called
        // Check if it got called 1 time no more or less
        verify(authService, times(1)).register(any(UserDTO.class));
    }

    @Test
    public void login_success() throws Exception {
        // Arrange
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("testuser");
        authRequest.setPassword("testpassword");
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("token", "token");
        when(authService.login(any(AuthRequest.class))).thenReturn("token");

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }

    @Test
    public void getToken_success() throws Exception {
        // Arrange
        String uuid = "some-uuid";
        String expectedToken = "generatedToken";
        when(authService.generateToken(any(String.class))).thenReturn(expectedToken);

        // Act
        ResponseEntity<String> response = authController.getToken(uuid);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedToken);
    }
    
    @Test
    public void validateToken_success() throws Exception {
        // Arrange
        Map<String, String> token = new HashMap<>();
        token.put("token", "token");
        Map<String,String> expectedResult =new HashMap<>();
        expectedResult.put("status", "valid");
        when(authService.validate(any(String.class))).thenReturn(true);

        // Act
        ResponseEntity<Map<String,String>> response = authController.validate(token);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }
    
    @Test
    public void validateToken_failed() throws Exception {
        // Arrange
        Map<String, String> token = new HashMap<>();
        token.put("token", "token");
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("status", "invalid");
        when(authService.validate(any(String.class))).thenReturn(false);

        // Act
        ResponseEntity<Map<String, String>> response = authController.validate(token);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }
}
