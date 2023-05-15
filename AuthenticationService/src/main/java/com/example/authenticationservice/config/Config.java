package com.example.authenticationservice.config;

import com.example.authenticationservice.models.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @implNote Esta clase es usada para configurar el AuthenticationManager y el AuthenticationProvider
 */
@Configuration
@RequiredArgsConstructor
public class Config {

    /**
     * @implNote Respositorio que permite el acceso a los datos almacenados de los usuarios
     */
    private final UserRepository repository;

    /**
     * @implNote Este metodo permite obtener los datos de un usuario
     * @return Retorna un objeto de tipo UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * @implNote Este metodo permite configurar el AuthenticationProvider
     * @return Retorna un objeto de tipo AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * @implNote Este metodo permite configurar el AuthenticationManager
     * @param config Objeto de tipo AuthenticationConfiguration
     * @return Retorna un objeto de tipo AuthenticationManager
     * @throws Exception Excepcion que se lanza si no se puede obtener el AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * @implNote Este metodo permite configurar el PasswordEncoder
     * @return Retorna un objeto de tipo PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
