package com.graduationProject.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AuthenticationApplication.class);
		app.run(args);
	}

}
