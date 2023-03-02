package com.mockproject.service;

import com.mockproject.dto.mapper.UnitDTOMapper;
import com.mockproject.repository.UnitRepository;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UnitService implements IUnitService {
    private final UnitRepository repository;

    private final UnitDTOMapper mapper;
}
