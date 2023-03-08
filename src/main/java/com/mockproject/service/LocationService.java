package com.mockproject.service;

import com.mockproject.entity.Location;
import com.mockproject.repository.LocationRepository;
import com.mockproject.service.interfaces.ILocationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository repository;

    @Override
    public List<Location> findLocationByTrainingClassID(Long id) {
        return repository.findDistinctAllByListTowersListTrainingClassUnitInformationsTrainingClassId(id);
    }
}
