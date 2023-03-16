package com.mockproject.repository;

import com.mockproject.entity.Location;
import com.mockproject.entity.Tower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface TowerRepository extends JpaRepository<Tower, Long> {
    Optional<Tower> findByIdAndStatus(long id, Boolean status);

    List<Tower> findByLocationAndStatus(Location location, boolean status);
}
