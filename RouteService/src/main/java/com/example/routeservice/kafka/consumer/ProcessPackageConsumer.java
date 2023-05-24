package com.example.routeservice.kafka.consumer;

import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.routeservice.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProcessPackageConsumer {

    @Autowired
    private PathService pathService;

    @KafkaListener(topics = "processPackage", containerFactory = "processPackageKafkaListenerContainerFactory")
    public void consume(ProcessPackageDTO processPackageDTO){
        pathService.processPackage(processPackageDTO);
    }

}
