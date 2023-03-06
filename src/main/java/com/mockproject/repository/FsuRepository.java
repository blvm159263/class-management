package com.mockproject.repository;

import com.mockproject.entity.Fsu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FsuRepository extends JpaRepository<Fsu, Long> {

    List<Fsu> findByStatus(boolean status);
}
