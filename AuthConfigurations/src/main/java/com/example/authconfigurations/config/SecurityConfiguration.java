package com.example.authconfigurations.config;

import com.example.authconfigurations.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @apiNote Esta clase es un componente de configuación para la seguridad implementada en los distinos servicios
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    /**
     * @implNote Este es el filtro que se encarga de interceptar las peticiones que llegan al servidor y verificar si el token es válido
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * @implNote Este método es el encargado de configurar la seguridad de los servicios
     * @param http Es el objeto que se encarga de configurar la seguridad de los servicios
     * @return Retorna un objeto de tipo SecurityFilterChain
     * @throws Exception Es la excepción que se lanza cuando ocurre un error en la configuración de la seguridad
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/v1/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
