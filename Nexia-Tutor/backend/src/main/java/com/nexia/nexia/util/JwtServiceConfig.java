package com.nexia.nexia.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.basedomain.basedomain.Shared.jwtService;

@Configuration
public class JwtServiceConfig {

    @Bean
    public jwtService jwtService() {
        return new jwtService(); // You may need to pass dependencies if necessary
    }
}
