package com.example.authenticationservice.repository;

import com.example.authenticationservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @implNote Esta interfaz representa el repositorio de datos de la tabla "role" en la base de datos.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * @implNote Este método permite buscar un rol por su id.
     * @param id El id del rol a buscar.
     * @return El rol encontrado.
     */
    Role findById(int id);

    public Role findByName(String name);

}
