package com.example.clientservice.service;

import com.example.clientservice.repository.PackageOnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageOnOrderService {

    @Autowired
    private PackageOnOrderRepository packageOnOrderRepository;
}
