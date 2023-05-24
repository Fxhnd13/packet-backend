package com.example.clientservice.controller;

import com.example.clientservice.service.PackageOnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-service/v1/packages-on-order")
public class PackageOnOrderController {

    @Autowired
    private PackageOnOrderService packageOnOrderService;
}
