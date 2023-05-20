package com.example.basedomains.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDTO {

    private Integer id;
    private String name;
    private String description;
    private int packagesOnRoute;
    private boolean isActive;
    private boolean isDeleted;
    private List<CheckpointDTO> checkpoints;
}
