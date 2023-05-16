package com.example.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String nit;
    private String email;
    private String fullname;
    private String numberPhone;
    private int age;
    private String address;
    private int idUser;
    private boolean priority;
}
