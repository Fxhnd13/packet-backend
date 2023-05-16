package com.example.checkpointservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Table(name = "checkpoint")
@NoArgsConstructor
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

    @Column(name = "deleted")
    private boolean isDeleted;

}
