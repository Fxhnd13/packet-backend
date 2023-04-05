package com.example.checkpointservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "package_information")
public class PackageInformation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_package")
    private int packageId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_checkpoint")
    private Checkpoint checkpoint;

    @Column(name = "arrival_timestamp")
    private Date arrivalTimestamp;

    @Column(name = "exit_timestamp")
    private Date exitTimestamp;

}
