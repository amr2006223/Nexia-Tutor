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
                .route("report-generation", r -> r.path("/report-generation/**").uri("lb://report-generation")) // Route to Report
                                                                                            // Generation
                                                                                            // service

                .route("nexia-tutor", r -> r.path("/nexia-tutor/**").uri("lb://nexia-tutor")) // Route to Nexia-Tutor
                                                                                              // service
                .route("screening", r -> r.path("/screening/**").uri("http://localhost:8000"))// Route to screening
                                                                                              // service
                .route("scraping", r -> r.path("/scraping/**").uri("http://localhost:5001"))// Route to webscraping
                .route("extracting", r -> r.path("/extracting/**").uri("http://localhost:5000")) // service

                .build();
    }
}
