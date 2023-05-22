package com.example.basedomains.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PackageOnCheckpointList {

    private List<PackageOnCheckpointDTO> packageOnCheckpointDTO = new ArrayList<>();

}
