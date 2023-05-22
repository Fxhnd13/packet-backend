package com.example.checkpointservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
