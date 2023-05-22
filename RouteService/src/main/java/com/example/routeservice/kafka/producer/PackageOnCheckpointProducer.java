package com.example.routeservice.kafka.producer;

import com.example.basedomains.dto.PackageOnCheckpointDTO;
import com.example.basedomains.dto.PackageOnCheckpointList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PackageOnCheckpointProducer {

    @Autowired
    private KafkaTemplate<String, PackageOnCheckpointList> kafkaTemplate;

    @Value(value = "packageOnCheckpoint")
    private String topic;
    public void sendPackageOnCheckpoint(PackageOnCheckpointList packageOnCheckpointList ){
        kafkaTemplate.send(topic, packageOnCheckpointList);
    }
}

