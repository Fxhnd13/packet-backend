package com.example.authenticationservice.controllers;

import com.example.authenticationservice.auth.AuthenticationRequest;
import com.example.authenticationservice.auth.AuthenticationResponse;
import com.example.authenticationservice.service.AuthenticationService;
import com.example.authenticationservice.auth.RegisterRequest;
import com.example.basedomains.constants.Constants;
import com.example.basedomains.exception.Error;
import com.example.basedomains.exception.NameAlreadyRegisteredException;
import com.example.basedomains.exception.RequiredFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * @apiNote Esta clase es el controlador de la autenticación, en ella se definen los endpoints
 */
@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/auth")
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
        try{
            return ResponseEntity.ok(service.register(request));
        } catch (RequiredFieldException | NameAlreadyRegisteredException e){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try{
            return ResponseEntity.ok(service.authenticate(request));
        } catch (BadCredentialsException e){
            return new ResponseEntity(new Error("Nombre de usuario o contraseña incorrectos."),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
