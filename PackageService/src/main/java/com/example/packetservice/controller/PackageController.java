package com.example.packetservice.controller;

import com.example.basedomains.dto.PayDTO;
import com.example.packetservice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/package-service/v1/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping("/pay/bank")
    public ResponseEntity<String> requestBankPayment(@RequestBody PayDTO payDTO){
        return packageService.requestBankPayment(payDTO);
    }

    @PostMapping("/pay/cash")
    public ResponseEntity<String> requestCashPayment(@RequestBody int packageId){
        return packageService.requestCashPayment(packageId);
    }
}
