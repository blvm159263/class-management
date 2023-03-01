package com.mockproject.repository;

import com.mockproject.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetail, Long> {
}
