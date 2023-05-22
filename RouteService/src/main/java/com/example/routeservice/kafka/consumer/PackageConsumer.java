package com.example.routeservice.kafka.consumer;

import com.example.basedomains.dto.OrderDTO;
import com.example.routeservice.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PackageConsumer {

    @Autowired
    private RouteService routeService;

    @KafkaListener(topics = "packageOnRoute", containerFactory = "orderKafkaListenerContainerFactory")
    public void consume(OrderDTO orderDTO){
        routeService.addPackage(orderDTO.getPackages());
    }
}
