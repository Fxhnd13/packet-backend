package com.example.checkpointservice.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "fee_type")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
