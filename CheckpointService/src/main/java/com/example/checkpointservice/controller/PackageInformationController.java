package com.example.checkpointservice.controller;

import com.example.checkpointservice.service.PackageInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkpoint-service/v1/packages-information")
public class PackageInformationController {

    @Autowired
    private PackageInformationService packageInformationService;
}
