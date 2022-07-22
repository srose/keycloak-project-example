package com.acme.backend.springboot.consent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ConsentProfileApiSpringbootApp {

	public static void main(String[] args) {
		SpringApplication.run(ConsentProfileApiSpringbootApp.class, args);
	}

}
