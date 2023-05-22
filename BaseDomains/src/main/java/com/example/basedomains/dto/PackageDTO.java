package com.example.basedomains.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackageDTO {

    private int id;
    private double weight;
    private LocalDate incomeDate;
    private LocalDate deliveryDate;
    private String deliveryAddress;
    private int routeId;
    private boolean prioritized;
    private double fee;
    private double prioritizedFee;
}
