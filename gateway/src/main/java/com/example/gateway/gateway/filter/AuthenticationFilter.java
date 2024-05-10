package com.example.gateway.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    // Autowiring RouteValidator and RestTemplate beans
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // Checks if the requested endpoint is secured
            // Uses the RouteValidator bean to determine if the endpoint is open or secured
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // Checks if the request contains an Authorization Header
                // Throws an exception if the header is missing
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    System.out.println("Missing Authorization Header");
                    throw new RuntimeException("Missing Authorization Header");
                }
                // Extracts the JWT token from the Authorization Header
                // Throws an exception if the authentication header is malformed
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer "))
                    authHeader = authHeader.substring(7);
                else
                    throw new RuntimeException("Wrong Authentication Header");
                // Validates the JWT token by making a request to the identity Service
                // microservice
                // Throws an exception if the token is invalid
                // Continues with the filter chain if the token is valid
                try {
                    restTemplate.postForObject("http://localhost:8080/identity-service/auth/validate", authHeader,
                            String.class);
                } catch (HttpClientErrorException e) {
                    throw e;
                }
            }
            return chain.filter(exchange);
        });
    }

    // Configuration class for the AuthenticationFilter
    public static class Config {
    }
}