package com.mockproject.service;

import com.mockproject.entity.Level;
import com.mockproject.entity.Role;
import com.mockproject.entity.User;
import com.mockproject.repository.LevelRepository;
import com.mockproject.service.interfaces.ILevelService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LevelService implements ILevelService {

    private final LevelRepository repository;
    public String getLevelCodeById(long id){
        String levelcode = repository.getLevelById(id).get().getLevelCode();
        return levelcode;
    }

    @Override
    public long getLevelByLevelCode(String levelCode) {
        long levelId = repository.getLevelByLevelCode(levelCode).get().getId();
        return levelId;
    }


}
