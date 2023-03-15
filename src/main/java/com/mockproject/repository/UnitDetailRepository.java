package com.mockproject.repository;

import com.mockproject.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetail, Long> {

    Optional<List<UnitDetail>> findByUnitIdAndStatus(long unitId, boolean status);

    Optional<UnitDetail> findByIdAndStatus(long unitDetailId, boolean status);

    List<UnitDetail> getListUnitDetailByUnitId(long idUnit);
}
