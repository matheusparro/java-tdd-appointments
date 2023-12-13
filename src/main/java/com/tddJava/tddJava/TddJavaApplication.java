package com.tddJava.tddJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"models"} )
@EnableJpaRepositories(basePackages = {"repositories"})
public class TddJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TddJavaApplication.class, args);
	}

}
