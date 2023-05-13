package com.example.checkpointservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.checkpointservice.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/checkpoints")
public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;

    @GetMapping("/")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Holaa");
    }
}
