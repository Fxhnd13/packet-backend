package com.example.notificationservice.controller;

import com.example.notificationservice.service.StakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification-service/v1/stakeholders")
public class StakeholderController {

    @Autowired
    private StakeholderService stakeholderService;
}
