package com.example.authenticationservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/is-authenticated")
public class AuthController {

    @GetMapping
    public ResponseEntity<String> isAuthenticated(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<String> roles = new ArrayList<>();

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                roles.add(authority.getAuthority());
            }

            return ResponseEntity.ok(roles.get(0));
    }

}
