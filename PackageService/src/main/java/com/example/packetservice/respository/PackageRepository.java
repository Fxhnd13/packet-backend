package com.example.packetservice.respository;

import com.example.packetservice.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {

    public Package findPackageById(int id);
}
