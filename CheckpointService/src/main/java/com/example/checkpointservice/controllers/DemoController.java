package com.example.checkpointservice.controllers;

import com.example.basedomain.auth.annotation.RoleValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class DemoController {

    @GetMapping("/hello-admin")
    public ResponseEntity<String> helloAdmin(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok("Hola mundo desde servicio de puntos de control con permisos de administrador y operador");
    }

    @GetMapping("/hello-operator")
    @RoleValidation({"OPERATOR"})
    public ResponseEntity<String> helloOperator(){
        return ResponseEntity.ok("Hola mundo desde servicio de puntos de control con permisos de operador");
    }

    @GetMapping("/hello-client")
    @RoleValidation({"CLIENT"})
    public ResponseEntity<String> helloClient(){
        return ResponseEntity.ok("Hola mundo desde servicio con permisos de cliente");
    }
}
