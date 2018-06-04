package com.designedperfectly.passwd;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PasswdApp {

	// This application provides a password validation service.
	// The service is configurable via inversion of control using Spring Boot dependency injection.
	// The service checks a text string for compliance to any number of password validation rules.
	// The rules currently known are listed in PasswdValidation class.

    public static void main(String[] args) {
        SpringApplication.run(PasswdApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Started Spring Boot Password Validation");

        };
    }
}