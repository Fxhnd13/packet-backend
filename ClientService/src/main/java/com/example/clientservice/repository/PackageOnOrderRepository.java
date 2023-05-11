package com.example.clientservice.repository;

import com.example.clientservice.model.PackageOnOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageOnOrderRepository extends JpaRepository<PackageOnOrder, Integer> {
}
