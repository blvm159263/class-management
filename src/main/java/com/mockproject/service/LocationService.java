package com.mockproject.service;

import com.mockproject.service.interfaces.ILocationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LocationService implements ILocationService {
}
