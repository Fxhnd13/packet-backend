package com.example.checkpointservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "checkpoint")
public class Checkpoint {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "length")
    private String length;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean isActive;
}
