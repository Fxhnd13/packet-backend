package com.example.packetservice.service;

import com.example.packetservice.respository.CumulativeFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CumulativeFeeService {
    @Autowired
    private CumulativeFeeRepository cumulativeFeeRepository;
}
