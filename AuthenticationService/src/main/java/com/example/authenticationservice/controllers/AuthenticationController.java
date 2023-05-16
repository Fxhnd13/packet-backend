package com.example.authenticationservice.controllers;

import com.example.authenticationservice.auth.AuthenticationRequest;
import com.example.authenticationservice.auth.AuthenticationResponse;
import com.example.authenticationservice.service.AuthenticationService;
import com.example.authenticationservice.auth.RegisterRequest;
import com.example.authenticationservice.source.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @apiNote Esta clase es el controlador de la autenticación, en ella se definen los endpoints
 */
@RestController
@RequestMapping("/v1/auth")
@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
public class AuthenticationController {

    @Autowired
    private  AuthenticationService service;

    /**
     * @apiNote Este endpoint se encarga de registrar un usuario
     * @param request Es el objeto que contiene los datos del usuario a registrar
     * @return Retorna un objeto de tipo AuthenticationResponse
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * @apiNote Este endpoint se encarga de autenticar un usuario
     * @param request Es el objeto que contiene los datos del usuario a autenticar
     * @return Retorna un objeto de tipo AuthenticationResponse
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

}
