package com.mockproject.repository;

import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetail, Long> {

    List<UnitDetail> findByUnitAndStatus(Unit unit, boolean status);
}
