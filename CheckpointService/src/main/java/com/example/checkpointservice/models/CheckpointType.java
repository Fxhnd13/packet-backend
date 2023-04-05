package com.example.checkpointservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "checkpoint_type")
public class CheckpointType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
