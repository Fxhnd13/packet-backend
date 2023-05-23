package com.example.packetservice.respository;

import com.example.packetservice.model.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {

    public Package findPackageById(int id);
    @Query(value="SELECT * FROM package WHERE CAST(id AS TEXT) LIKE ?1%  AND delivery_date IS NOT NULL", nativeQuery = true)
    public Page<Package> findByIdStartingWithAndDeliveryDateNull(String id, Pageable pageable);

    @Query(value="SELECT * FROM package WHERE delivery_date IS NOT NULL", nativeQuery = true)
    public Page<Package> findByIDeliveryDateNull(Pageable pageable);

    @Query(value="SELECT * FROM package WHERE CAST(id AS TEXT) LIKE ?1% ", nativeQuery = true)
    public Page<Package> findByIdLike(String pattern, Pageable pageable);

}
