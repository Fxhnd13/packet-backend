package com.example.basedomains.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FeeDTO {

    private Double fee;
    private Double prioritizedFee;
}
