package com.example.clientservice.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import com.example.basedomains.dto.ClientEvent;
import org.springframework.stereotype.Service;


@Service
public class ClientConsumer {

    //"
   @KafkaListener(topics = "client")
    public void consume(ClientEvent clientEvent){
       System.out.println("MICROSERVICIO DE CLIENTES");
       System.out.println(clientEvent.getClient().toString());
    }
}
