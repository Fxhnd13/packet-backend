package com.example.authenticationservice.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @implNote AuthenticationResponse es una clase POJO que contiene el token
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * @implNote token es el token que se genera al momento de autenticar al usuario
     */
    private String token;

}
