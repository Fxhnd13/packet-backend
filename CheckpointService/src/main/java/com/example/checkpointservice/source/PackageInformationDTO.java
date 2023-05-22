package com.example.checkpointservice.source;


import com.example.basedomains.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PackageInformationDTO {

    private int idPackage;
    private Checkpoint checkpoint;

}
