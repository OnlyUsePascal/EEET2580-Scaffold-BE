package com.example.lab_test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LabTest1Application {
	public static void main(String[] args) {
		SpringApplication.run(LabTest1Application.class, args);
	}
}
