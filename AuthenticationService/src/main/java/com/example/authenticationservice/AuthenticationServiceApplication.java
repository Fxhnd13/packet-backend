package com.example.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @implNote Esta clase es la encargada de iniciar el servicio de autenticación
 */
@SpringBootApplication(scanBasePackages = {"com.example.authconfigurations","com.example.authenticationservice"})
@EnableDiscoveryClient
public class AuthenticationServiceApplication {

	/**
	 * @implNote Método principal de la clase
	 * @param args Argumentos de la línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

}
