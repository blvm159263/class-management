package com.mockproject.repository;

import com.mockproject.entity.Session;
import com.mockproject.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> getListUnitBySessionId(long idSession);

    Optional<List<Unit>> findUnitBySessionIdAndStatus(long sessionId, boolean status);

    List<Unit> findBySession(Session session);  Optional<Unit> findByIdAndStatus(long id, boolean status);

    Optional<List<Unit>> findAllBySessionIdAndStatus(long sessionId, boolean status);

}
