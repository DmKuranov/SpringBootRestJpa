package ru.dmkuranov.springbootrestjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class SpringBootRestJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestJpaApplication.class, args);
	}
}

