package com.example.clientservice.controller;

import com.example.clientservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-service/v1/cards")
public class CardController {

    @Autowired
    private CardService cardService;
}
