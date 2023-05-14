package com.example.checkpointservice.service;


import com.example.checkpointservice.model.Fee;
import com.example.checkpointservice.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    public void add(Fee fee){
        feeRepository.save(fee);
    }
}
