package com.example.packetservice.kafka.consumer;

import com.example.basedomains.dto.OrderDTO;
import com.example.packetservice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class PackageIdConsumer {

    @Autowired
    private PackageService packageService;

    @KafkaListener(topics = "delivered", containerFactory = "packageIdKafkaListenerContainerFactory")
    public void consume(Integer id){
        packageService.deliver(id);
    }

}
