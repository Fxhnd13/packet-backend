package com.example.authenticationservice.controllers;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.service.UserService;
import com.example.basedomains.constants.Constants;
import com.example.basedomains.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = Constants.URL_FRONTEND, allowCredentials = "true")
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<User> updateUser(@RequestBody User user){
        try{
            userService.update(user);
            return ResponseEntity.ok().build();
        } catch (ElementNoExistsException e ){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        try{
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ElementNoExistsException e){
            return  new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @RoleValidation("ADMIN")
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(userService.getAll(pattern, page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @RoleValidation("ADMIN")
    public ResponseEntity<User> getUser(@PathVariable int id
    ){
        try{
            return new ResponseEntity<User>(userService.getUser(id), HttpStatus.OK);
        }  catch (ElementNoExistsException e) {
            return new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
