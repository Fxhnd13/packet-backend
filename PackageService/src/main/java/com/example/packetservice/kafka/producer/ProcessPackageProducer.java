package com.example.packetservice.kafka.producer;

import com.example.basedomains.dto.ProcessPackageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessPackageProducer {
    @Autowired
    private KafkaTemplate<String, ProcessPackageDTO> kafkaTemplate;

    @Value(value = "processPackage")
    private String topic;

    public void sendProcessPackage(ProcessPackageDTO processPackageDTO){
        kafkaTemplate.send(topic, processPackageDTO);
    }
}
