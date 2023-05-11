package com.example.routeservice.controller;

import com.example.routeservice.service.PathService;
import com.netflix.discovery.converters.Auto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route-service/v1/paths")
public class PathController {

    @Auto
    private PathService pathService;
}
