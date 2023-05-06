package com.example.clientservice.Models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paquete_orden")
public class PackageOnOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_package")
    private int packageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_order")
    private Order order;

}
