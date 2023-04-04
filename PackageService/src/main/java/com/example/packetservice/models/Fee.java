package com.example.packetservice.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_priority")
    private Priority priority;
}
