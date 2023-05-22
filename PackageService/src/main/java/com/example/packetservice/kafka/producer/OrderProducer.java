package com.example.packetservice.kafka.producer;

import com.example.basedomains.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    @Autowired
    private KafkaTemplate<String, OrderDTO> kafkaTemplate;

    @Value(value = "packageOnOrder")
    private String topicToClient;

    @Value(value = "packageOnRoute")
    private String topicToRoute;

    public void sendOrderToClientService(OrderDTO orderDTO){
        kafkaTemplate.send(topicToClient, orderDTO);
    }

    public void sendOrderToRouteService(OrderDTO orderDTO){
        kafkaTemplate.send(topicToRoute, orderDTO);
    }



}

