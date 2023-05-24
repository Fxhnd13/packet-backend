package com.example.paymentsservice.kafka.consumer;

import com.example.basedomains.dto.PayDTO;
import com.example.paymentsservice.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PayConsumer {

    @Autowired
    private PayService payService;

    @KafkaListener(topics = "packageOnPay", containerFactory = "payKafkaListenerContainerFactory")
    public void consume(PayDTO payDTO){
        payService.savePay(payDTO);
    }

}
