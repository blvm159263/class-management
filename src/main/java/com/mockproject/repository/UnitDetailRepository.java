package com.mockproject.repository;

import com.mockproject.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetail, Long> {

    List<UnitDetail> findByUnitIdAndStatus(long unitId, boolean status);

    UnitDetail findByIdAndStatus(long unitDetailId, boolean status);
}
