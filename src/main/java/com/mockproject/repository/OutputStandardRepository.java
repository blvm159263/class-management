package com.mockproject.repository;

import com.mockproject.entity.OutputStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputStandardRepository extends JpaRepository<OutputStandard, Long> {
}
