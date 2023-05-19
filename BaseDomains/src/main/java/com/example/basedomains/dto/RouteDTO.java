package com.example.basedomains.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteDTO {

    private String name;
    private String description;
    private List<CheckpointDTO> checkpoints;

}
