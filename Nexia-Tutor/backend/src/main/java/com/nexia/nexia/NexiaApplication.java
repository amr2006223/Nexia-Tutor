package com.nexia.nexia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NexiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexiaApplication.class, args);
	}

}
