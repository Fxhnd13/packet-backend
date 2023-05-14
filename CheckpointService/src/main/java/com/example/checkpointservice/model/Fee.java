package com.example.checkpointservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fee_checkpoint")
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
