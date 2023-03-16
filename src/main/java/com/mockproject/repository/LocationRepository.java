package com.mockproject.repository;

import com.mockproject.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByStatus(boolean status);

    Optional<Location> findFirstByLocationNameAndStatus(String name, boolean status);



    Optional<Location> findByStatusAndId(boolean status, long id);

    List<Location> findAllByStatus(boolean status);
}
