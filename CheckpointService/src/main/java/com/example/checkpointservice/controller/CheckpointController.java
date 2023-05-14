package com.example.checkpointservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.checkpointservice.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/checkpoints")
public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;


    @PostMapping("/")
    @RoleValidation({"ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public Checkpoint addCheckpoint(@RequestBody CheckpointDTO checkpoint){
        return  checkpointService.add(checkpoint);
    }



}
