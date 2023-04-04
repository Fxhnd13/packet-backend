package com.example.packetservice.models;

import jakarta.persistence.*;

@Entity
@Table(name = "prioridad")
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
