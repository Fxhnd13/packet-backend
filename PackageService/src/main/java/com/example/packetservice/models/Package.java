package com.example.packetservice.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="package")
@Data
public class Package {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double weight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_priority", nullable = false)
    private Priority priority;

    @Column(name = "income_date")
    private Date incomeDate;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fee")
    private Fee fee;

}
