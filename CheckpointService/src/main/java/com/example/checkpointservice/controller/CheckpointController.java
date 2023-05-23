package com.example.checkpointservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.basedomains.constants.Constants;
import com.example.basedomains.exception.*;
import com.example.basedomains.dto.CheckpointDTO;
import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.service.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin (origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/checkpoints")
public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;

    @GetMapping("/test")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }

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
        } catch (NameAlreadyRegisteredException | RequiredFieldException | NotANumberException |
                 NoEmptyCheckpointException e ){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Checkpoint> deleteCheckpoint(@PathVariable int id)   {
        try{
            checkpointService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ElementNoExistsException e){
            return  new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        } catch (NoEmptyCheckpointException ne){
            return new ResponseEntity(ne.getError(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
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

    @GetMapping("/active-checkpoints")
    @RoleValidation("ADMIN")
    public ResponseEntity<Page<Checkpoint>> getActiveCheckpoints(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(checkpointService.getAllActive(pattern, page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @RoleValidation("ADMIN")
    public ResponseEntity<CheckpointDTO> getCheckpoint(@PathVariable int id
    ){
        try{
            return checkpointService.getCheckpoint(id);
        }  catch (ElementNoExistsException e) {
            return new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
