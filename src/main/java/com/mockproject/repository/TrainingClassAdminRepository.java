package com.mockproject.repository;

import com.mockproject.entity.TrainingClassAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingClassAdminRepository extends JpaRepository<TrainingClassAdmin, Long> {
}
