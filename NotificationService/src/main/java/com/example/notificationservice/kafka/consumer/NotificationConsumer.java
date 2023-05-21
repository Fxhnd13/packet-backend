package com.example.notificationservice.kafka.consumer;

import com.example.basedomains.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotificationConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "autNotification")
    public void consume(NotificationDTO  notificationDTO){
        SimpleMailMessage mail = new SimpleMailMessage();
       // mail.setFrom(null);
        mail.setTo(notificationDTO.getEmail());
        mail.setSubject(notificationDTO.getMessage());
        mail.setText(notificationDTO.getMessage());
        javaMailSender.send(mail);
    }
}
