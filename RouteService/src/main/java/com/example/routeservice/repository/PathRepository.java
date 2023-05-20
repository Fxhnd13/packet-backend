package com.example.routeservice.repository;

import com.example.routeservice.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {

    @Transactional
    public void deleteAllByRouteId(int id);

    public List<Path> findByRouteId(int id);
}
