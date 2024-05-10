package com.example.gateway.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
/**
 * RouteValidator class to validate incoming requests and determine if they
 * require authentication.
 */
public class RouteValidator {
        // Defining open end points that wouldn't need authentication
        public static final List<String> openApiEndpoints = List.of(
                        "/identity-service/auth/register",
                        "/eureka",
                        "/identity-service/auth/login",
                        "/identity-service/auth/**");

        // Predicate to check if a request is secured (requires authentication) by
        // looping openApiEndpoints
        public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
                        .stream()
                        .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
