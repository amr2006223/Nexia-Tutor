package com.example.identityservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.models.UserCredentials;
import com.example.identityservice.services.AuthService;

import org.springframework.web.bind.annotation.GetMapping;
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
    public String getToken(@RequestBody String uuid) {
        return authService.generateToken(uuid);
    }
    
    @PostMapping("/validate")
    public boolean validate(@RequestBody String token) {
        return authService.validate(token);
    }
    
}
