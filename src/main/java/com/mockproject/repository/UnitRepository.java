package com.mockproject.repository;

import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findBySessionAndStatusOrderByUnitNumberAsc(Session session, boolean status);
}
