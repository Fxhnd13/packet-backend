package com.example.routeservice.repository;

import com.example.routeservice.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    public Route findByNameAndIsDeletedFalse(String name);
    public Route findByNameAndIdNotAndIsDeletedFalse(String name, int id);

    public Route findByIdAndIsDeletedFalse(int id);

    public Page<Route> findByIsDeletedFalse(Pageable pageable);
    public Page<Route> findByIsDeletedFalseAndIsActiveTrue(Pageable pageable);

    @Query(value="SELECT * FROM route WHERE CAST(id AS TEXT) LIKE ?1%  AND deleted=false", nativeQuery = true)
    public Page<Route> findByIdStartingWith(int id, Pageable pageable);

    @Query(value="SELECT * FROM route WHERE CAST(id AS TEXT) LIKE ?1%  AND deleted=false AND active=true", nativeQuery = true)
    public Page<Route> findByIdStartingWithAndIsActiveTrue(int id, Pageable pageable);

    public Page<Route> findByIsDeletedFalseAndNameIgnoreCaseContaining(String name, Pageable pageable);

    public Page<Route> findByIsDeletedFalseAndIsActiveTrueAndNameIgnoreCaseContaining(String name, Pageable pageable);

    public List<Route> findByIsDeletedFalseAndIsActiveTrue();
}
