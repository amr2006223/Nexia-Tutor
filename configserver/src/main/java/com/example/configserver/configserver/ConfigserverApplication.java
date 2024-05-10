package com.example.configserver.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
/**
 * Indicates that config server application is serving as a configuration
 * server.
 * This annotation enables the configuration server functionality,
 * allowing the application to serve externalized configuration properties to
 * client applications.
 */
@EnableConfigServer
public class ConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}

}
