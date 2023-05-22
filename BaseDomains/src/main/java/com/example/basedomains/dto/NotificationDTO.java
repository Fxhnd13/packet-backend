package com.example.basedomains.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDTO {

    private String message;
    private String email;
}
