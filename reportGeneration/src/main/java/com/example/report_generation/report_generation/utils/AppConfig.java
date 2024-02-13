package com.example.report_generation.report_generation.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    // @Bean
    // public JSONFileWriter jsonFileWriter() {
    //     return new JSONFileWriter();
    // }
    
    // @Bean
    // public UserService userService() {
    //     return new UserService();
    // }
}