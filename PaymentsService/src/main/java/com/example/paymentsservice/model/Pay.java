package com.example.paymentsservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "pending")
    private boolean pending;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "card_number")
    private String cardNumber;

    @PrePersist
    public void prePersist() {
        date = LocalDate.now();
        pending = true;
    }

}
