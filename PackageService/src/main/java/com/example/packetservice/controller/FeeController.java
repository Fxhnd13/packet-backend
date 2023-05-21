package com.example.packetservice.controller;

import com.example.packetservice.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/package-service/v1/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

}
