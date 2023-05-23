package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @apiNote Esta clase contiene la clase principal para la aplicación "Gateway" de Spring Boot.
 * @implNote Esta clase contiene la anotación @SpringBootApplication, que es una combinación de las anotaciones @Configuration, @EnableAutoConfiguration y @ComponentScan.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
