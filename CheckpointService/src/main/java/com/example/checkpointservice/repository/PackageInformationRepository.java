package com.example.checkpointservice.repository;

import com.example.checkpointservice.model.Checkpoint;
import com.example.checkpointservice.model.PackageInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PackageInformationRepository extends JpaRepository<PackageInformation, Integer> {

    public long  countByCheckpointIdAndExitTimestamp(int id, Date date);

    public Page<PackageInformation> findByExitTimestampIsNull(Pageable pageable);

    @Query(value="SELECT * FROM package_information WHERE CAST(id_package AS TEXT) LIKE ?1%  AND exit_timestamp IS NULL", nativeQuery = true)
    public Page<PackageInformation> findByIdStartingWithAndExitTimestampIsNull(String id, Pageable pageable);

    public PackageInformation findByPackageIdAndExitTimestampIsNull(int packageId);

    public PackageInformation findFirstByPackageIdOrderByIdDesc(int packageId);
}
