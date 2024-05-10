package com.example.identityservice.controllers;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basedomain.basedomain.dto.UserDTO;
import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.services.AuthService;
import com.example.identityservice.services.JwtService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/identity-service/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userDTO = authService.register(userDTO);
        if(userDTO == null) return new ResponseEntity<>("Error While Adding Users",HttpStatus.OK);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest);
        if(token == null) return  new ResponseEntity<>("Invalid Credentials",HttpStatus.OK);
        Map<String,String> responseBody = new HashMap<>();
        responseBody.put("token", token);
        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> getToken(@RequestBody String uuid) {
        return new ResponseEntity<String>(authService.generateToken(uuid),HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validate(@RequestBody Map<String,String> body) {
        Map<String, String> response = new HashMap<>();
        if (authService.validate(body.get("token"))) {
            response.put("status", "valid");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("status", "invalid");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/token/getPayload")
    public String getPayload(@RequestBody Map<String,String> body) {
        String token = body.get("token");
        return jwtService.extractUUID(token);
    }
    
}
