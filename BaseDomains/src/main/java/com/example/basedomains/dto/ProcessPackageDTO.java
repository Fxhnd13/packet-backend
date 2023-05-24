package com.example.basedomains.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPackageDTO {

    private int idPackage;
    private int idCurrentCheckpoint;
    private int idNextCheckpoint;
    private int idRoute;

    private boolean delivered;

}
