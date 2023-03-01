package com.mockproject.service;

import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UnitService implements IUnitService {
}
