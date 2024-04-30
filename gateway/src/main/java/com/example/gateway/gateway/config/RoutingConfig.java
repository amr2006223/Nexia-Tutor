package com.example.gateway.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.gateway.gateway.filter.AuthenticationFilter;

@Configuration
public class RoutingConfig {
    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("report-generation", r -> r
                        .path("/report-generation/**")
                        // .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://report-generation"))

                .route("nexia-tutor", r -> r
                        .path("/nexia-tutor/**")
                        // .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://nexia-tutor"))

                .route("identity-service", r -> r
                        .path("/identity-service/**")
                        .uri("lb://identity-service"))

                .route("screening", r -> r
                        .path("/screening/**")
                        // .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:5002"))

                .route("scraping", r -> r
                        .path("/scraping/**")
                        // .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:5001"))

                .route("extracting", r -> r
                        .path("/extracting/**")
                        // .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:5000"))

                .build();
    }
}
