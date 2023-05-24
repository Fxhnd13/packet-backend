package com.example.checkpointservice.repository;

import com.example.checkpointservice.model.Checkpoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Integer> {

    public Checkpoint findByNameAndIsDeletedFalse(String name);
    public Checkpoint findByNameAndIdNotAndIsDeletedFalse(String name, int id);

    public Checkpoint findByIdAndIsDeletedFalse(int id);

    public Page<Checkpoint> findByIsDeletedFalse(Pageable pageable);
    public Page<Checkpoint> findByIsDeletedFalseAndIsActiveTrue(Pageable pageable);

    @Query(value="SELECT * FROM checkpoint WHERE CAST(id AS TEXT) LIKE ?1%  AND deleted=false", nativeQuery = true)
    public Page<Checkpoint> findByIdStartingWith(int id, Pageable pageable);

    @Query(value="SELECT * FROM checkpoint WHERE CAST(id AS TEXT) LIKE ?1%  AND deleted=false AND active=true", nativeQuery = true)
    public Page<Checkpoint> findByIdStartingWithAndIsActiveTrue(int id, Pageable pageable);

    public Page<Checkpoint> findByIsDeletedFalseAndNameIgnoreCaseContaining(String name, Pageable pageable);

    public Page<Checkpoint> findByIsDeletedFalseAndIsActiveTrueAndNameIgnoreCaseContaining(String name, Pageable pageable);

}
