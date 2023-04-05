package com.example.notificationservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stakeholder")
public class Stakeholder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_client")
    private int clientId;

    @Column(name = "id_package")
    private int packageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_message_type")
    private MessageType messageType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_media")
    private Media media;

}
