package com.example.paymentsservice.controller;

import com.example.paymentsservice.request.UpdatePayRequest;
import com.example.paymentsservice.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/pay-service/v1/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @PutMapping("/")
    public ResponseEntity<Boolean> updatePay(@RequestBody UpdatePayRequest request){
        return payService.updatePay(request);
    }

}
