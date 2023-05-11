package com.example.clientservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "nit")
    private String nit;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "number_phone")
    private String numberPhone;

    @Column(name = "age")
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "priority")
    private boolean priority;
    //private UserType userType;
}
