package com.example.basedomains.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayDTO {

    private String cardNumber;
    private double amount;
    private int orderId;
    private boolean accepted;
}
