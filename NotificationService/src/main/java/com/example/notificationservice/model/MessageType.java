package com.example.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "message_type")
public class MessageType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "message")
    private String message;

}
