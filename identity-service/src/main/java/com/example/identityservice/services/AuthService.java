package com.example.identityservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basedomain.basedomain.dto.Constants;
import com.basedomain.basedomain.dto.UserDTO;
import com.example.identityservice.dto.AuthRequest;
import com.example.identityservice.kafka.UserProducer;
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
    @Autowired
    private UserProducer userProducer;

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
            return null;
        }
    }
    
    public UserDTO register(UserDTO userDTO) {
        try{
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            UserCredentials credentials = new UserCredentials();
            credentials.setUsername(userDTO.getUsername());
            credentials.setPassword(userDTO.getPassword());
            credentials.setRole("USER");
            credentials = repository.save(credentials);
            userDTO.setId(credentials.getId());
            userDTO.setToken(jwtService.generateToken(credentials.getId()));
            if(!userProducer.broadcastUser(userDTO, Constants.Status.ADD, "Adding Users")){
                return null;
            }
            return userDTO;
        }catch(Exception e){
            return null;
        }
    }
}
