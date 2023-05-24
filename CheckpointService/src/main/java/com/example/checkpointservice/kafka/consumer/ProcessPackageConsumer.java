package com.example.checkpointservice.kafka.consumer;

import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.checkpointservice.service.PackageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProcessPackageConsumer {

    @Autowired
    private PackageInformationService packageInformationService;

    @KafkaListener(topics = "processPackageOnCheckpoint", containerFactory = "processPackageKafkaListenerContainerFactory")
    public void consume(ProcessPackageDTO processPackageDTO){
        packageInformationService.processPackage(processPackageDTO);
    }
}
