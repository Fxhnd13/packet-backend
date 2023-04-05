package com.example.checkpointservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "fee")
public class Fee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_checkpoint")
    private Checkpoint checkpoint;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="id_fee_type")
    private FeeType feeType;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private Date date;

}
