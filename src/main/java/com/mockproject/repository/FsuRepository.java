package com.mockproject.repository;

import com.mockproject.entity.Fsu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FsuRepository extends JpaRepository<Fsu, Long> {
}
