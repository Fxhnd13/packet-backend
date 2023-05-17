package com.example.clientservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import com.example.basedomains.dto.ClientEvent;
import org.springframework.stereotype.Service;


@Service
public class ClientConsumer {

    //, groupId = "spring.kafka.consumer.group-id"
 /*  @KafkaListener(topics = "spring.kafka.topic.name")
    public void consume(ClientEvent event){
       System.out.println("MICROSERVICIO DE CLIENTES");
       System.out.println(event.getClient().toString());
    }*/
}
