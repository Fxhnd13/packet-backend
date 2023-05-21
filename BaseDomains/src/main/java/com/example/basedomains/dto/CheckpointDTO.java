package com.example.basedomains.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckpointDTO {

    private int id;
    private String latitude;
    private String length;
    private String name;
    private boolean isActive;
    private boolean isDeleted;
    private Double fee;

}
