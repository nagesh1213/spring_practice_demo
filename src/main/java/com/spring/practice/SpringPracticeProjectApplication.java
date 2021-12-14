package com.spring.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringPracticeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPracticeProjectApplication.class, args);
	}

}
