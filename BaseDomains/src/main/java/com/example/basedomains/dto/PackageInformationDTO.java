package com.example.basedomains.dto;


import lombok.*;

import java.time.LocalDate;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PackageInformationDTO {

    //Package Data
    private int idPackage;
    private LocalDate arrivalDate;

    //Checkpoint Data
    private int idCheckpoint;
    private String checkpointName;
    private String latitude;
    private String length;

}
