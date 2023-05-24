package com.example.paymentsservice.repository;

import com.example.paymentsservice.model.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, Integer> {

    public Pay findPayById(int id);
}
