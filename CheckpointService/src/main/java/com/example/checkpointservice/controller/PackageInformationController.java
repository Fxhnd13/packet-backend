package com.example.checkpointservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.service.PackageInformationService;
import com.example.basedomains.dto.PackageInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/packages-information")
public class PackageInformationController {

    @Autowired
    private PackageInformationService packageInformationService;

    @GetMapping("/")
    @RoleValidation("OPERATOR")
    public ResponseEntity<Page<PackageInformationDTO>> getPackagesInformation(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(packageInformationService.getPackages(pattern, page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @RoleValidation("OPERATOR")
    public ResponseEntity<Checkpoint> getPackageInformation(
            @PathVariable int id
    ){
        try{
            return ResponseEntity.ok(packageInformationService.getCheckpointByPackageId(id));
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
