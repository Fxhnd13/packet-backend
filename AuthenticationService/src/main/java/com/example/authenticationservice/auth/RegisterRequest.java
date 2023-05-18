package com.example.authenticationservice.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
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

    private String fullname;
    private String password;
    private String username;
    private String nit;
    private String email;
    private String numberPhone;
    private Integer age;
    private String address;
}
