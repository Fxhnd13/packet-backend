package com.example.packetservice.kafka.consumer;

import com.example.basedomains.dto.OrderDTO;
import com.example.packetservice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class OrderConsumer {

    @Autowired
    private PackageService packageService;

    @KafkaListener(topics = "order", containerFactory = "orderKafkaListenerContainerFactory")
    public void consume(OrderDTO orderDTO){
        packageService.addPackages(orderDTO);
    }

}
