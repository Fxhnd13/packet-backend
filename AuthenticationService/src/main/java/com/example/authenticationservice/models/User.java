package com.example.authenticationservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @implNote Esta clase representa el modelo de datos de la tabla "Usuario" en la base de datos.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    /**
     * @implNote Esta propiedad representa el id de la tabla "Usuario" en la base de datos.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * @implNote Esta propiedad representa el nombre de la tabla "Usuario" en la base de datos.
     */
    @Column(name = "username", unique = true)
    private String username;

    /**
     * @implNote Esta propiedad representa el nombre completo de la tabla "Usuario" en la base de datos.
     */
    @Column(name = "fullname")
    private String fullname;

    /**
     * @implNote Esta propiedad representa el correo electrónico de la tabla "Usuario" en la base de datos.
     */
    @Column(name = "password")
    private String password;

    /**
     * @implNote Esta propiedad representa el rol de la tabla "Usuario" en la base de datos.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role")
    private Role role;

    /**
     * @implNote Este método obtiene el nombre de usuario del usuario.
     * @return El nombre de usuario del usuario.
     */
    @Override
    public String getUsername(){
        return this.username;
    }

    /**
     * @implNote Este método obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword(){
        return this.password;
    }

    /**
     * @implNote Este método obtiene los roles del usuario.
     * @return Los roles del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    /**
     * @implNote Este método obtiene si la cuenta del usuario ha expirado.
     * @return Si la cuenta del usuario ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @implNote Este método obtiene si la cuenta del usuario está bloqueada.
     * @return Si la cuenta del usuario está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @implNote Este método obtiene si las credenciales del usuario han expirado.
     * @return Si las credenciales del usuario han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @implNote Este método obtiene si el usuario está habilitado.
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
