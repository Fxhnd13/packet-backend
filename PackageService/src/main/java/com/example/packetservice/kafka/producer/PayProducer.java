package com.example.packetservice.kafka.producer;

import com.example.basedomains.dto.PayDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayProducer {

    @Autowired
    private KafkaTemplate<String, PayDTO> kafkaTemplate;

    @Value(value = "packagePaymentNotification")
    private String topicToPay;

    public void sendPayToPaymentService(PayDTO payDTO){
        kafkaTemplate.send(topicToPay, payDTO);
    }

}
