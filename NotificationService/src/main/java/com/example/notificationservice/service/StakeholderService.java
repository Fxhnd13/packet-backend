package com.example.notificationservice.service;

import com.example.notificationservice.repository.StakeholderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StakeholderService {

    @Autowired
    private StakeholderRepository stakeholderRepository;
}
