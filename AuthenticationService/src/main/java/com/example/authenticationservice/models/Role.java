package com.example.authenticationservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @implNote Esta clase representa el modelo de datos de la tabla "role" en la base de datos.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    /**
     * @implNote Esta propiedad representa el id de la tabla "role" en la base de datos.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * @implNote Esta propiedad representa el nombre de la tabla "role" en la base de datos.
     */
    @Column(name = "name")
    private String name;

    /**
     * @implNote Esta propiedad representa la descripción de la tabla "role" en la base de datos.
     */
    @Column(name = "description")
    private String description;

}
