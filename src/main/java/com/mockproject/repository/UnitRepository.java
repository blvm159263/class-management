package com.mockproject.repository;

import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<List<Unit>> findBySessionAndStatusOrderByUnitNumber(Session session, Boolean status);

    Optional<Unit> findByIdAndStatus(long id, Boolean status);

    List<Unit> findBySession(Session session);
}
