package com.example.basedomains.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PackageOnCheckpointDTO {

    private int checkpointId;
    private int packageId;
}
