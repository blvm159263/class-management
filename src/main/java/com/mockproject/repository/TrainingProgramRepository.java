package com.mockproject.repository;

import com.mockproject.dto.TrainingProgramDTO;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    @Query("SELECT tp FROM TrainingProgram tp WHERE tp.creator.fullName LIKE CONCAT('%', :query, '%') OR tp.name LIKE CONCAT('%', :query, '%')")
    public List<TrainingProgram> searchProgramP(String query);
}
