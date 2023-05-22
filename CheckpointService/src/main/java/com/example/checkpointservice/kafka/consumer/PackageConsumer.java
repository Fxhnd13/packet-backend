package com.example.checkpointservice.kafka.consumer;

import com.example.basedomains.dto.PackageOnCheckpointList;
import com.example.checkpointservice.service.PackageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PackageConsumer {

    @Autowired
    private PackageInformationService packageInformationService;

    @KafkaListener(topics = "packageOnCheckpoint", containerFactory = "packageKafkaListenerContainerFactory")
    public void consume(PackageOnCheckpointList packages){
       packageInformationService.addPackage(packages.getPackageOnCheckpointDTO());
    }
}
