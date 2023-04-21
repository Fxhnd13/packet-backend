package com.example.checkpointservice.controllers;

import com.example.basedomain.auth.annotation.RoleValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class DemoController {

    @GetMapping("/hello-admin")
    @RoleValidation({"ADMINISTRADOR","OPERADOR"})
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hola mundo desde servicio de puntos de control con permisos de administrador y operador");
    }

    @GetMapping("/hello-client")
    @RoleValidation({"CLIENTE"})
    public ResponseEntity<String> helloClient(){
        return ResponseEntity.ok("Hola mundo desde servicio de puntos de control con permisos de cliente");
    }
}
