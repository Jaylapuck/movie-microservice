package com.forest.microservices.composite.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("com.forest")
public class MovieCompositeServiceApplication {

	@Bean
	RestTemplate restTemplate() {return  new RestTemplate();}

	public static void main(String[] args) {
		SpringApplication.run(MovieCompositeServiceApplication.class, args);
	}

}
