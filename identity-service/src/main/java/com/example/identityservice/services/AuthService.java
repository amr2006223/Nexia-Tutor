package com.example.identityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.models.UserCredentials;
import com.example.identityservice.repository.UserCredentialsRepository;

@Service
public class AuthService {
    @Autowired 
    private UserCredentialsRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String saveUser(UserCredentials credentials){
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        repository.save(credentials);
        return "User saved";
    }

    public String generateToken(String uuid){
        return jwtService.generateToken(uuid);
    }
    
    public boolean validate(String token) {
        return jwtService.validateToken(token);
    }
    
    public String login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), 
                authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            UserCredentials user = (UserCredentials) authenticate.getPrincipal();
            return generateToken(user.getId());
        }else{
            throw new RuntimeException("invalid access");
        }
    }
}
