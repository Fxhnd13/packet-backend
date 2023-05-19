package com.example.routeservice.repository;

import com.example.routeservice.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    public Route findByNameAndIsDeletedFalse(String name);
}
