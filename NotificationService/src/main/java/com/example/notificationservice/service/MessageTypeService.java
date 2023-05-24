package com.example.notificationservice.service;

import com.example.notificationservice.repository.MessageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageTypeService {

    @Autowired
    private MessageTypeRepository messageTypeRepository;
}
