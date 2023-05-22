package com.example.authenticationservice.kafka.producer;

import com.example.basedomains.dto.ClientDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientProducer {

    @Autowired
    private KafkaTemplate<String, ClientDTO> kafkaTemplate;

    @Value(value = "client")
    private String topic;

    public void sendClient(ClientDTO clientEvent){
        kafkaTemplate.send(topic, clientEvent);
    }
}
