package com.example.checkpointservice.kafka.producer;

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
public class PackageIdProducer {

    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplate;

    @Value(value = "delivered")
    private String topic;

    public void sendPackageId(Integer  id){
        kafkaTemplate.send(topic, id);
    }
}
