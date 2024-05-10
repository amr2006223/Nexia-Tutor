package com.example.gateway.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    /**
     * Defines a bean for RestTemplate, which can be used to make HTTP requests.
     * 
     * @return RestTemplate instance
     */
    public RestTemplate template() {
        return new RestTemplate();
    }
}
