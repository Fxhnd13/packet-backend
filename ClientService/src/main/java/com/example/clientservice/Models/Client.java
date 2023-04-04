package com.example.clientservice.Models;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_priority")
    private Priority priority;
    //private UserType userType;
}
