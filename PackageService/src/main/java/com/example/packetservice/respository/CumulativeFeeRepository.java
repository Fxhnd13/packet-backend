package com.example.packetservice.respository;

import com.example.packetservice.model.CumulativeFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CumulativeFeeRepository extends JpaRepository<CumulativeFee, Integer> {
}