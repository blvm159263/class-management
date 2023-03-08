package com.mockproject.service;

import com.mockproject.dto.LocationDTO;
import com.mockproject.mapper.LocationMapper;
import com.mockproject.repository.LocationRepository;
import com.mockproject.service.interfaces.ILocationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository locationRepo;

    @Override
    public List<LocationDTO> getAllLocation() {
        return locationRepo.findAll().stream().map(LocationMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
