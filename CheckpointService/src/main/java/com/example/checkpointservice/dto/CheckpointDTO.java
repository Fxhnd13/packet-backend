package com.example.checkpointservice.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckpointDTO {

    private int id;
    private String latitude;
    private String length;
    private String name;
    private boolean isActive;
    private boolean isDeleted;
    private double fee;

}
