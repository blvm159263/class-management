package com.mockproject.service;

import com.mockproject.dto.LevelDTO;
import com.mockproject.entity.Level;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.mapper.LevelMapper;
import com.mockproject.repository.LevelRepository;
import com.mockproject.service.interfaces.ILevelService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LevelService implements ILevelService {

    private final LevelRepository repository;

    @Override
    public LevelDTO getLevelById(long id){
        Optional<Level> level = repository.getLevelById(id);
        if (level.isPresent()){
            return LevelMapper.INSTANCE.toDTO(level.get());
        }

        return null;
    }

    @Override
    public long getLevelByLevelCode(String levelCode) {
        long levelId = repository.getLevelByLevelCode(levelCode).get().getId();
        return levelId;
    }

    @Override
    public List<LevelDTO> getAll() {
        return repository.findAll().stream().map(LevelMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

}
