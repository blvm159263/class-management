package com.mockproject.repository;

import com.mockproject.entity.OutputStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutputStandardRepository extends JpaRepository<OutputStandard, Long> {

    List<OutputStandard> findByStatusAndStandardCodeContainingIgnoreCase(boolean status, String name);
}
