package com.example.basedomains.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private int id;
    private LocalDate creationDate;
    private double total;
    private String nit;
    private String address;
    private String name;
    private List<PackageDTO> packages;

}
