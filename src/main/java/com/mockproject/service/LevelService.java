package com.mockproject.service;

import com.mockproject.dto.LevelDTO;
import com.mockproject.mapper.LevelMapper;
import com.mockproject.repository.LevelRepository;
import com.mockproject.service.interfaces.ILevelService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LevelService implements ILevelService {
    private final LevelRepository repository;

    @Override
    public List<LevelDTO> getAll() {
        return repository.findAll().stream().map(LevelMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

}
