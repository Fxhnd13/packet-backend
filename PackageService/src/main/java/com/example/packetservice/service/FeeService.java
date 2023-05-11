package com.example.packetservice.service;

import com.example.packetservice.respository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;
}
