package com.example.checkpointservice.repository;

import com.example.checkpointservice.model.PackageInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PackageInformationRepository extends JpaRepository<PackageInformation, Integer> {

    public long  countByCheckpointIdAndExitTimestamp(int id, Date date);

    public Page<PackageInformation> findByExitTimestampIsNull(Pageable pageable);
}
