package com.example.report_generation.report_generation.utils;

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
    public jwtService jwtService() {
        return new jwtService(); // You may need to pass dependencies if necessary
    }
    
    // @Bean
    // public UserService userService() {
    //     return new UserService();
    // }
}