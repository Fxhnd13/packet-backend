package com.example.notificationservice.controller;

import com.example.notificationservice.service.MessageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification-service/v1/message-types")
public class MessageTypeController {

    @Autowired
    private MessageTypeService messageTypeService;
}
