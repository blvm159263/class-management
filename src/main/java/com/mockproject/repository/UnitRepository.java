package com.mockproject.repository;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findUnitBySessionIdAndStatus(long sessionId, boolean status);

    Unit findByIdAndSessionIdAndStatus(long id, long sessionId, boolean status);

}
