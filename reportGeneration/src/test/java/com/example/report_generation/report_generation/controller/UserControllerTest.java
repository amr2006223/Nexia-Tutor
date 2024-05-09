package com.example.report_generation.report_generation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;


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

import com.example.report_generation.report_generation.models.User;
import com.example.report_generation.report_generation.service.JwtService;
import com.example.report_generation.report_generation.service.PDFGeneratorService;
import com.example.report_generation.report_generation.service.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private PDFGeneratorService pdfGeneratorService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addUser_success() throws IOException {
        // Arrange
        String token = "testToken";
        String id = "testId";
        User newUser = new User(token, "testUserName", new ArrayList<>());
        User insertedUser = new User(id, "testUserName", new ArrayList<>());

        when(jwtService.extractUUID(anyString())).thenReturn(id);
        when(userService.insertUser(any(User.class), anyString())).thenReturn(insertedUser);
        when(pdfGeneratorService.generateDocumentInServer(any(User.class))).thenReturn(true);

        // Act
        ResponseEntity<User> response = userController.addUser(newUser);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(insertedUser);

        // Verify method calls
        verify(jwtService).extractUUID(anyString());
        verify(userService).insertUser(any(User.class), anyString());
        verify(pdfGeneratorService).generateDocumentInServer(any(User.class));
    }

    @Test
    public void getUser_success() throws IOException {
        // Arrange
        String id = "testId";
        User user = new User(id, "testUserName", new ArrayList<>());

        when(userService.getUserById(anyString(), anyString())).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUser(id);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(user);

        // Verify method call
        verify(userService).getUserById(anyString(), anyString());
    }

    @Test
    public void deleteUserById_success() throws IOException {
        // Arrange
        String userId = "testUserId";
        User deletedUser = new User(userId, "testUserName", new ArrayList<>());

        when(userService.deleteUserById(anyString(), anyString())).thenReturn(deletedUser);

        // Act
        ResponseEntity<User> response = userController.deleteUserById(userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(deletedUser);

        // Verify method call
        verify(userService).deleteUserById(anyString(), anyString());
    }

}
