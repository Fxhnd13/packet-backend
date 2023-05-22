package com.example.notificationservice.service;

import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public String sendNotification() {
        //Aquí debe ir la lógica para enviar el correo, de momento dejarlo como un endpoint de prueba
        return "Notification sent successfully";
    }
}
