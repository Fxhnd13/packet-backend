package com.example.clientservice.controller;

import com.example.clientservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-service/v1//orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
}
