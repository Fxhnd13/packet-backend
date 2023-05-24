package com.example.authenticationservice.repository;

import com.example.authenticationservice.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    public Optional<User> findByUsernameAndIsDeletedFalse(String username);

    public Page<User> findByIsDeletedFalse(Pageable pageable);

    public Page<User> findByIsDeletedFalseAndUsernameIgnoreCaseContaining(String name, Pageable pageable);

    @Query(value="SELECT * FROM users WHERE CAST(id AS TEXT) LIKE ?1%  AND deleted=false", nativeQuery = true)
    public Page<User> findByIdStartingWithAndIsDeletedFalse(int id, Pageable pageable);

    public User findByIdAndIsDeletedFalse(int id);
}
