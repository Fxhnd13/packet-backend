package com.example.checkpointservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fee_type")
public class FeeType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
