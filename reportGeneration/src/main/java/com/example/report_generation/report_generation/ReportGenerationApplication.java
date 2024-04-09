package com.example.report_generation.report_generation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReportGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportGenerationApplication.class, args);
	}

}
