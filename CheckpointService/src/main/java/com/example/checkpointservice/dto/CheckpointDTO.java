package com.example.checkpointservice.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckpointDTO {

    private int id;
    private String latitude;
    private String length;
    private String name;
    private boolean isActive;
    private boolean isDeleted;
    private double fee;
}
