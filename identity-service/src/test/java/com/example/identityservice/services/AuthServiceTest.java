package com.example.identityservice.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.basedomain.basedomain.dto.Constants;
import com.basedomain.basedomain.dto.UserDTO;
import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.kafka.UserProducer;
import com.example.identityservice.models.UserCredentials;
import com.example.identityservice.repository.UserCredentialsRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @MockBean 
    private UserCredentialsRepository repository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserProducer userProducer;
    @InjectMocks
    private AuthService authService;


    @BeforeEach
    public void setUp() {
        // mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_success() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername("testuser");
        userDTO.setPassword("testpassword");

        UserCredentials user = new UserCredentials();
        user.setId("testId");

        when(userProducer.broadcastUser(any(UserDTO.class),any(Constants.Status.class),any(String.class))).thenReturn(true);
        when(repository.save(any(UserCredentials.class))).thenReturn(user);
        when(jwtService.generateToken(any(String.class))).thenReturn("testToken");
        when(passwordEncoder.encode(any(String.class))).thenReturn("testpassword");

        // Act
        UserDTO response = authService.register(userDTO);

        // Assert
        assertThat(response.getId()).isEqualTo("testId");
        assertThat(response.getToken()).isEqualTo("testToken");
        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getPassword()).isEqualTo("testpassword");
    }
    
    @Test
    public void login_success() throws Exception {
        // Arrange
       AuthRequest authRequest = new AuthRequest();
       authRequest.setUsername("testUser");
       authRequest.setPassword("testPassword");

       String expectedResult = "testToken";

       UserCredentials user = new UserCredentials();
       user.setId("testId");

       Authentication authentication = new UsernamePasswordAuthenticationToken(
            user, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authService.generateToken(user.getId()))
                .thenReturn("testToken");

        // Act
        String response = authService.login(authRequest);

        // Assert
        assertThat(response).isEqualTo(expectedResult);
    }
}
