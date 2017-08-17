package com.challenge.got.client.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.challenge.got.client.http.GameServiceClient;
import com.challenge.got.client.http.PlayerServiceClient;
import com.challenge.got.client.http.jersey.GameServiceClientJersey;
import com.challenge.got.client.http.jersey.PlayerServiceClientJersey;
import com.challenge.got.config.SpringBootConfig;

/**
 * This Spring Configuration class, is used for starting the application using Spring Boot
 */
@SpringBootApplication(scanBasePackages = { "com.challenge.got.client.controller", "com.challenge.got.client.http",
		"com.challenge.got.client.manager", "com.challenge.got.client.auth.repo" })
@EntityScan(basePackages = { "com.challenge.got.client.auth.model" })
public class SpringBootClientConfig extends SpringBootServletInitializer {

	private static final String BASE_URL = "http://localhost:8080/";

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { SpringBootClientConfig.class, WebMvcConfig.class }, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootClientConfig.class, SpringBootConfig.class);
	}

	/*
	 * Spring beans
	 */
	@Bean
	public PlayerServiceClient playerServiceClient() {
		return new PlayerServiceClientJersey(BASE_URL);
	}

	@Bean
	public GameServiceClient gameService() {
		return new GameServiceClientJersey(BASE_URL);
	}

	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}
}
