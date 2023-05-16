package com.example.authenticationservice.repository;

import com.example.authenticationservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Esta interfaz se utiliza para acceder a la base de datos y realizar operaciones CRUD en la tabla de usuarios.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return Usuario encontrado.
     */
    Optional<User> findByUsername(String username);

}
