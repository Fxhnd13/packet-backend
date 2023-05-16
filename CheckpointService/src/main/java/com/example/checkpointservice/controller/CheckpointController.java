package com.example.checkpointservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.checkpointservice.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.service.CheckpointService;
import com.example.checkpointservice.source.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/checkpoints")
public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;

    @PostMapping("/")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Checkpoint> addCheckpoint(@RequestBody CheckpointDTO checkpoint){
        try{
            return  checkpointService.add(checkpoint);
        } catch (NameAlreadyRegisteredException | RequiredFieldException | NotANumberException e ){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Checkpoint> updateCheckpoint(@RequestBody CheckpointDTO checkpoint){
        try{
            checkpointService.update(checkpoint);
            return ResponseEntity.ok().build();
        } catch (NameAlreadyRegisteredException | RequiredFieldException | NotANumberException | NoEmptyCheckpointException e ){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Checkpoint> deleteCheckpoint(@PathVariable int id) throws NoEmptyCheckpointException {
        try{
            checkpointService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ElementNoExistsException e){
            return  new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @RoleValidation("ADMIN")
    public ResponseEntity<Page<Checkpoint>> getCheckpoints(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(checkpointService.getAll(pattern, page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
