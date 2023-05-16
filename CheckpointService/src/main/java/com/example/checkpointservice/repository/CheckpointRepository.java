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

    public Checkpoint findByName(String name);
    public Checkpoint findByNameAndIdNot(String name, int id);

    public Optional<Checkpoint> findByIdAndIsDeleted(int id, boolean deleted);

    @Query(value="SELECT * FROM checkpoint WHERE CAST(id AS TEXT) LIKE ?1%", nativeQuery = true)
    public Page<Checkpoint> findByIdStartingWith(int id, Pageable pageable);
    public Page<Checkpoint> findByNameIgnoreCaseContaining(String name, Pageable pageable);

}
