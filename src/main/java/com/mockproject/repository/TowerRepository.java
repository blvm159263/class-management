package com.mockproject.repository;

import com.mockproject.entity.Location;
import com.mockproject.entity.Tower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TowerRepository extends JpaRepository<Tower, Long> {

    List<Tower> findByLocationAndStatus(Location location, boolean status);
}
