package com.example.gateway.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {
    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        // example for front end url to nexia-tutor port
        // http://localhost:8080/nexia-tutor/api/auth/gentoken/123321
        return builder.routes()
                // Route to Report Generation service
                .route("report-generation", r -> r.path("/report-generation/**").uri("lb://report-generation"))
                // Route to Nexia-Tutor service
                .route("nexia-tutor", r -> r.path("/nexia-tutor/**").uri("lb://nexia-tutor"))
                // Route to AuthenticationService service
                .route("identity-service", r -> r.path("/identity-service/**").uri("lb://identity-service"))
                // Route to screening service
                .route("screening", r -> r.path("/screening/**").uri("http://localhost:5002"))
                // Route to scraping service
                .route("scraping", r -> r.path("/scraping/**").uri("http://localhost:5001"))
                // Route to extracting service
                .route("extracting", r -> r.path("/extracting/**").uri("http://localhost:5000"))
                

                .build();
    }
}
