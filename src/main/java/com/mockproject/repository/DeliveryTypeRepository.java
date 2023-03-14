package com.mockproject.repository;

import com.mockproject.entity.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {
    Optional<DeliveryType> findByIdAndStatus(long id, Boolean status);
}
