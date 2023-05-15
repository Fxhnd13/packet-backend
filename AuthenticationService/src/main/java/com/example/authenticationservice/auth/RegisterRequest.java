package com.example.authenticationservice.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @implNote RegisterRequest es una clase POJO que se utiliza para recibir los datos de registro de un usuario
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * @implNote Nombre completo del usuario
     */
    private String fullname;
    /**
     * @implNote Correo electrónico del usuario
     */
    private String password;
    /**
     * @implNote Nombre de usuario del usuario
     */
    private String username;
    /**
     * @implNote Rol del usuario
     */
    private int roleId;

}
