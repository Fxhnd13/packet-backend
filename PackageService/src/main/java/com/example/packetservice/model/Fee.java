package com.example.packetservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "priority")
    private boolean priority;
}
