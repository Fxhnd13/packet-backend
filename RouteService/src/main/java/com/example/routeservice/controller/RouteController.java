package com.example.routeservice.controller;

import com.example.authconfigurations.auth.annotation.RoleValidation;
import com.example.basedomains.dto.RouteDTO;
import com.example.basedomains.exception.*;
import com.example.routeservice.model.Route;
import com.example.routeservice.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Route> addRoute(@RequestBody RouteDTO route){
        try{
            return  routeService.add(route);
        } catch (NameAlreadyRegisteredException | RequiredFieldException | EmptyRouteException e ){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Route> updateRoute(@RequestBody RouteDTO route){
        try{
            routeService.update(route);
            return ResponseEntity.ok().build();
        } catch (NameAlreadyRegisteredException | RequiredFieldException | NoEmptyRouteException | ElementNoExistsException e ){
            return new ResponseEntity(e.getError(),  HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @RoleValidation({"ADMIN"})
    public ResponseEntity<Route> deleteRoute(@PathVariable int id)  {
        try{
            routeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ElementNoExistsException e){
            return  new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        } catch (NoEmptyRouteException ne){
            return new ResponseEntity(ne.getError(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            return  new ResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    @RoleValidation("ADMIN")
    public ResponseEntity<Page<Route>> getRoutes(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(routeService.getAll(pattern, page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active-routes")
    @RoleValidation({"ADMIN", "OPERATOR"})
    public ResponseEntity<Page<Route>> getActiveRoutes(
            @RequestParam(required = false) String pattern,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            return new ResponseEntity<>(routeService.getAllActive(pattern, page, size), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*
    @GetMapping("/{id}")
    @RoleValidation("ADMIN")
    public ResponseEntity<RouteDTO> getRoute(@PathVariable int id
    ){
        try{
            return routeService.getRoute(id);
        }  catch (ElementNoExistsException e) {
            return new ResponseEntity(e.getError(), HttpStatus.NOT_FOUND);
        } catch(Exception e){
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
