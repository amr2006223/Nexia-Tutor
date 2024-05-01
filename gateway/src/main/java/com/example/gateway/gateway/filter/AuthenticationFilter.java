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
            // Checks if the endpoint in the request is one of the open endpoints
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // Checks if there is an Authorization Header
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    System.out.println("Missing Authorization Header");
                    throw new RuntimeException("Missing Authorization Header");
                }
                // Checks if the authorization header is a jwt token by checking if it starts
                // with bearer
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer "))
                    authHeader = authHeader.substring(7);
                else
                    throw new RuntimeException("Wrong Authentication Header");
                // Validate the token from identity Service microservice
                try {
                    restTemplate.postForObject("http://localhost:8080/identity-service/auth/validate", authHeader,
                            String.class);
                    // Throw error if not validated
                } catch (HttpClientErrorException e) {
                    throw e;
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}
