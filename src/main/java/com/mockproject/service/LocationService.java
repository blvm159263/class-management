package com.mockproject.service;

import com.mockproject.repository.LocationRepository;
import com.mockproject.service.interfaces.ILocationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository repository;

}
