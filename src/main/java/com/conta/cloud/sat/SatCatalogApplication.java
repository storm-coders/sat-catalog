package com.conta.cloud.sat;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class SatCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SatCatalogApplication.class, args);
	}
	
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
	    return new MethodValidationPostProcessor();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();	
	}
}
