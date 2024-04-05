package com.example.report_generation.report_generation.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.basedomain.basedomain.Shared.jwtService;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public jwtService jwtService(@Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.validity}") String validity) {
        return new jwtService(jwtSecret, validity);
    }
    
    // @Bean
    // public UserService userService() {
    //     return new UserService();
    // }
}