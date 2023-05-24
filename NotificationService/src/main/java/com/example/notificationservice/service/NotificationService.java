package com.example.notificationservice.service;

import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendNotification(String email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        // mail.setFrom(null);
        mail.setTo(email);
        mail.setSubject("Testing envío de correos");
        mail.setText("Este es un mensaje que se envía para los correso de prueba");
        javaMailSender.send(mail);
        return "Notification sent successfully";
    }
}
