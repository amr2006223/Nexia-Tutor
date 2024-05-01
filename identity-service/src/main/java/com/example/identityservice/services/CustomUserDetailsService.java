package com.example.identityservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.identityservice.models.UserCredentials;
import com.example.identityservice.repository.UserCredentialsRepository;
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    @Override
    public UserCredentials loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentials credentials = userCredentialsRepository.findByUsername(username).orElse(null);
        return credentials;
    }
    
}
