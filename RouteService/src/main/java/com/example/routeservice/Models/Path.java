package com.example.routeservice.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ruta_punto_de_control")
public class Path {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_route")
    private Route route;

    @Column(name = "id_initial_checkpoint")
    private int initialCheckpointId;

    @Column(name = "id_final_checkpoint")
    private int finalCheckpointId;

}
