package com.example.routeservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hola mundo desde servicio de rutas");
    }
}
