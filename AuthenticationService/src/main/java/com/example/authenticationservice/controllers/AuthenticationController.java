package com.example.authenticationservice.controllers;

import com.example.authenticationservice.auth.AuthenticationRequest;
import com.example.authenticationservice.auth.AuthenticationResponse;
import com.example.authenticationservice.auth.AuthenticationService;
import com.example.authenticationservice.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/authorities")
    public ResponseEntity<Authentication> getAuthorities(){
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }

}
