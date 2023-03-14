package com.mockproject.repository;

import com.mockproject.entity.OutputStandard;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitDetailRepository extends JpaRepository<UnitDetail, Long> {

    List<UnitDetail> findByStatusAndOutputStandardIn(boolean status, List<OutputStandard> osd);

    @Query("SELECT u FROM UnitDetail u " +
            "WHERE u.status = ?1 " +
            "AND u.unit.session.syllabus.id = ?2")
    List<UnitDetail> findUnitDetailBySyllabusId(boolean status, long syllabusId);

    Optional<List<UnitDetail>> findByUnitInAndStatus(List<Unit> units, Boolean status);

}
