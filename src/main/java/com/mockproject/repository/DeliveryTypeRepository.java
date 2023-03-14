package com.mockproject.repository;

import com.mockproject.entity.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {

    DeliveryType findByIdAndStatus(Long id, boolean status);

}
