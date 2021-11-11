package com.forest.microservices.core.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.forest")
public class MovieServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx= SpringApplication.run(MovieServiceApplication.class, args);
		String mysqlUri= ctx.getEnvironment().getProperty("spring.datasource.url");
		LOGGER.info("Connected to MySQL: " + mysqlUri);
	}


}
