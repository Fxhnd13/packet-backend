package com.example.authenticationservice.kafka;

import com.example.basedomains.dto.ClientEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Service
public class ClientProducer {

    private NewTopic topic;

    private KafkaTemplate<String, ClientEvent> kafkaTemplate;

    public ClientProducer(NewTopic topic, KafkaTemplate<String, ClientEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ClientEvent event){
        Message<ClientEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
