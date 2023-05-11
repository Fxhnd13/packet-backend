package com.example.checkpointservice.service;

import com.example.checkpointservice.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;
}
