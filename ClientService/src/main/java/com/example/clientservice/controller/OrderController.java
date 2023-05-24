package com.example.clientservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.basedomains.constants.Constants;
import com.example.basedomains.dto.OrderDTO;
import com.example.basedomains.exception.ElementNoExistsException;
import com.example.basedomains.exception.EmptyOrderException;
import com.example.clientservice.model.Order;
import com.example.clientservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    @RoleValidation({"OPERATOR", "CLIENT"})
    public ResponseEntity<Order> addOder(
            @RequestBody OrderDTO orderDTO)
    {
        try{
            return new ResponseEntity(orderService.add(orderDTO), HttpStatus.CREATED);
        } catch (ElementNoExistsException | EmptyOrderException e){
            return new ResponseEntity(e.getError(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
