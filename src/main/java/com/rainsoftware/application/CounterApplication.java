package com.rainsoftware.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"com.rainsoftware.application.config", "com.rainsoftware.application.service", "com.rainsoftware.application.controller"})
@EnableJpaRepositories(value = {"com.rainsoftware.application.domain.repository"})
@EntityScan(value = "com.rainsoftware.application.domain.model")
@SpringBootApplication
public class CounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CounterApplication.class, args);
	}
}
