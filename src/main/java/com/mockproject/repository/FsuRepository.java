package com.mockproject.repository;

import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.Fsu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FsuRepository extends JpaRepository<Fsu, Long> {

    Optional<Fsu> findByStatusAndId(boolean status, long id);

    List<Fsu> findAllByStatus(boolean status);
}
