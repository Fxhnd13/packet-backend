package com.example.basedomains.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayDTO {

    private double amount;
    private int orderId;
}
