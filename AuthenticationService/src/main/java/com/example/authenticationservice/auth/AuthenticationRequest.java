package com.example.authenticationservice.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @implNote AuthenticationRequest Es una clase POJO que se utiliza para asignar el cuerpo de la solicitud de inicio de sesión.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    /**
     * @implNote username es uno lo de los campos que se utilizan para iniciar sesión.
     */
    private String username;
    /**
     * @implNote password es uno lo de los campos que se utilizan para iniciar sesión.
     */
    private String password;

}
