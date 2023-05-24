package com.example.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProcessPackageRequest {

    private int idPackage;
    private int idCheckpoint;

}
