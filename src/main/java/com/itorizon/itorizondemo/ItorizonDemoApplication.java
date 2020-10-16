package com.itorizon.itorizondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan({"com.itorizon.itorizondemo.controller", "com.itorizon.itorizondemo.service", "com.itorizon.itorizondemo.util"})
@EnableMongoRepositories({"com.itorizon.itorizondemo.dao", "com.itorizon.itorizondemo.model"})
public class ItorizonDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItorizonDemoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

}
