package com.example.eurekaserver.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
/**
 * Indicates that this Spring Boot application is serving as a Eureka server.
 * This annotation enables the Eureka server functionality,
 * allowing the application to register other microservices and manage service
 * discovery.
 */
@EnableEurekaServer
public class EurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverApplication.class, args);
	}

}
