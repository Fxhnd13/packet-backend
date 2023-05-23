package com.example.packetservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.basedomains.constants.Constants;
import com.example.basedomains.dto.ProcessPackageDTO;
import com.example.basedomains.dto.ProcessPackageRequest;
import com.example.packetservice.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/delivered")
    @RoleValidation("OPERATOR")
    public ResponseEntity<Page<Package>> getPackages(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity(packageService.getDeliveredPackages(pattern, page, size), HttpStatus.OK);

        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    @RoleValidation("OPERATOR")
    public ResponseEntity<Page<Package>> processPackage(
            @RequestBody ProcessPackageRequest processPackageRequest
            ){
        try{
            packageService.processPackage(processPackageRequest);
            return ResponseEntity.ok().build();

        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
