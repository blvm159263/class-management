package com.mockproject.service;

import com.mockproject.dto.mapper.LevelDTOMapper;
import com.mockproject.repository.LevelRepository;
import com.mockproject.service.interfaces.ILevelService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class LevelService implements ILevelService {

    private final LevelRepository repository;

    private final LevelDTOMapper mapper;
}
