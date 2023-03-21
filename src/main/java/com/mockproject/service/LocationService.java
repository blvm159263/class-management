package com.mockproject.service;

import com.mockproject.dto.LocationDTO;
import com.mockproject.mapper.LocationMapper;
import com.mockproject.repository.LocationRepository;
import com.mockproject.service.interfaces.ILocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository repository;

    @Override
    public List<LocationDTO> listAllTrue() {
        return repository.findByStatus(true).stream().map(LocationMapper.INSTANCE::toDTO).toList();
    }
}
