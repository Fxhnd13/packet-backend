package com.example.clientservice.kafka.consumer;

import com.example.basedomains.dto.OrderDTO;
import com.example.clientservice.service.PackageOnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @Autowired
    private PackageOnOrderService packageOnOrderService;

    @KafkaListener(topics = "packageOnOrder", containerFactory = "orderKafkaListenerContainerFactory")
    public void consume(OrderDTO orderDTO){
        packageOnOrderService.addPackagesOnOrder(orderDTO);
    }
}
