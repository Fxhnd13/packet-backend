package com.example.authenticationservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/is-authenticated")
public class AuthController {

    @GetMapping
    public ResponseEntity<Boolean> isAuthenticated(){
        return ResponseEntity.ok(true);
    }

}
