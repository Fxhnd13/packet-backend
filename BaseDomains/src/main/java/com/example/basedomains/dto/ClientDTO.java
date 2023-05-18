package com.example.basedomains.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private String nit;
    private String email;
    private String fullname;
    private String numberPhone;
    private int age;
    private String address;
    private int idUser;
}
