package com.example.identityservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
import org.springframework.http.ResponseEntity;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthService authService;


    @InjectMocks
    private AuthController authController;


    @BeforeEach
    public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_success() throws Exception {
        //Arrange
        Date date = new Date();
        UserDTO userDTO = new UserDTO("testId", "testuser", "testpassword", date, "Egyptian", false, "null", "1234",
                "USER");
        when(authService.register(any(UserDTO.class))).thenReturn(userDTO);
        //Act
        ResponseEntity<?> response = authController.register(userDTO);
        //Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userDTO);
        verify(authService, times(1)).register(any(UserDTO.class));
    }

    @Test
    public void register_failed() throws Exception {
        // Arrange
        String expectedResult = "Error While Adding Users";
        UserDTO userDTO = new UserDTO();
        when(authService.register(any(UserDTO.class))).thenReturn(null);
        // Act
        ResponseEntity<?> response = authController.register(userDTO);
        // Assert
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResult);
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
