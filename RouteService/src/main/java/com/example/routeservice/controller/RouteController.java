package com.example.routeservice.controller;

import com.example.routeservice.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route-service/v1/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;
}
