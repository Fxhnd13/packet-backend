package com.example.checkpointservice.dto;

import com.example.checkpointservice.model.Checkpoint;
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

    public CheckpointDTO(Checkpoint checkpoint, double fee){
        this.id = checkpoint.getId();
        this.latitude = checkpoint.getLatitude();
        this.length = checkpoint.getLength();
        this.name = checkpoint.getName();
        this.isActive = checkpoint.isActive();
        this.isDeleted = checkpoint.isDeleted();;
        this.fee = fee;
    }
}
