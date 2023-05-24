package com.example.packetservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.basedomains.constants.Constants;
import com.example.basedomains.dto.FeeDTO;
import com.example.packetservice.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

   @RoleValidation("ADMIN")
    @GetMapping("/")
    public ResponseEntity<FeeDTO> getFee(){
        try{
            return ResponseEntity.ok(feeService.getFee());
        } catch (Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RoleValidation("ADMIN")
    @PutMapping("/")
    public ResponseEntity<FeeDTO> updateFee(@RequestBody FeeDTO feeDTO){
        try{
            feeService.updateFee(feeDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return  new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
