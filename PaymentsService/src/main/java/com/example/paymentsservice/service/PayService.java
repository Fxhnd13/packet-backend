package com.example.paymentsservice.service;

import com.example.paymentsservice.model.Pay;
import com.example.paymentsservice.repository.PayRepository;
import com.example.paymentsservice.request.UpdatePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PayService {

    @Autowired
    private PayRepository payRepository;

    public ResponseEntity<Boolean> updatePay(UpdatePayRequest request){
        try{
            Pay pay = payRepository.findPayById(request.getPayId());
            pay.setPending(false);
            pay.setAccepted(request.isAccept());
            payRepository.save(pay);
            return ResponseEntity.ok().body(true);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
        }
    }

    public void savePay(double amount, int orderId){
        try{
            new Pay();
            Pay pay = Pay.builder()
                    .amount(amount)
                    .orderId(orderId)
                    .pending(true)
                    .accepted(false).build();
            payRepository.save(pay);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}