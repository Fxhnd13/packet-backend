package com.example.checkpointservice.service;

import com.example.checkpointservice.repository.FeeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeTypeService {

    @Autowired
    private FeeTypeRepository feeTypeRepository;
}
