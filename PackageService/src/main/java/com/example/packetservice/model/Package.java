package com.example.packetservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name="package")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Package {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "weight")
    private double weight;

    @Column(name = "priority")
    private boolean priority;

    @Column(name = "income_date")
    private LocalDate incomeDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fee")
    private Fee fee;

    @Column(name = "id_route")
    private int routeId;

}
