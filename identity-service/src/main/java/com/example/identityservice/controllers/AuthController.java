package com.example.identityservice.controllers;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.models.UserCredentials;
import com.example.identityservice.services.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/identity-service/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody UserCredentials credentials) {
        authService.saveUser(credentials);
        return "added User";
    }
    
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }
    @PostMapping("/generate")
    public ResponseEntity<String> getToken(@RequestBody String uuid) {
        return new ResponseEntity<>( authService.generateToken(uuid),HttpStatus.OK);
    }
    
    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validate(@RequestBody String token) {
        Map<String, String> response = new HashMap<>();
        if (authService.validate(token)) {
            response.put("status", "valid");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("status", "invalid");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
}
