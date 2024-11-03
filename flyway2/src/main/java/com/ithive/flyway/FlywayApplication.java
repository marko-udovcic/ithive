package com.ithive.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = "com.ithive.flyway.controller")
public class FlywayApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FlywayApplication.class, args);
		Environment env = context.getEnvironment();

		System.out.println("Application is running on port: " + env.getProperty("server.port"));
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> changePort() {
		return factory -> factory.setPort(8089);
	}
}