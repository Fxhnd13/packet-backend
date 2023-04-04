package com.example.packetservice.models;

import jakarta.persistence.*;

@Entity
public class CumulativeFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_package")
    private Package packet; //No utilicé package porque package es una palabra reservada de Java :sadge:

    @Column(name = "amount")
    private double amount;
}
