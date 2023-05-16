package com.example.checkpointservice.controller;

import com.example.checkpointservice.service.FeeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkpoint-service/v1/fee-types")
public class FeeTypeController {

    @Autowired
    private FeeTypeService feeTypeService;
}
