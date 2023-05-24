package com.example.clientservice.controller;

import com.example.basedomains.constants.Constants;
import com.example.basedomains.dto.OrderDTO;
import com.example.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;





}
