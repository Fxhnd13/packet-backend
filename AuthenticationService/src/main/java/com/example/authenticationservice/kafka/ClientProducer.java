package com.example.authenticationservice.kafka;

import com.example.basedomains.dto.ClientEvent;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientProducer {

    private KafkaTemplate<String, ClientEvent> kafkaTemplate;

    @Value(value = "${kafka.topic.name}")
    private String topic;

    public void sendMessage(ClientEvent clientEvent){
        kafkaTemplate.send(this.topic, clientEvent);
    }



    /*public void sendMessage(ClientEvent event){
        Message<ClientEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }*/
}
