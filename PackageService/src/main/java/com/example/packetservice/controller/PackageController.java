package com.example.packetservice.controller;

import com.example.packetservice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/package-service/v1/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;
}
