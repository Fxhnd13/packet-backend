package com.example.authenticationservice.kafka.producer;

import com.example.basedomains.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    @Autowired
    private KafkaTemplate<String, NotificationDTO> kafkaTemplate;

    @Value(value = "autNotification")
    private String topic;

    public void sendNotification(NotificationDTO notificationDTO){
        kafkaTemplate.send(topic, notificationDTO);
    }
}
