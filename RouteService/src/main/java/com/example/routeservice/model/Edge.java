package com.example.routeservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="edge")
public class Edge {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_initial_checkpoint")
    private int initialCheckpointId;

    @Column(name = "id_final_checkpoint")
    private int finalCheckpointId;

}
