package com.mockproject.service.interfaces;

import com.mockproject.dto.LevelDTO;
import com.mockproject.entity.Level;

import java.util.List;
import java.util.Optional;

public interface ILevelService {
    public List<LevelDTO> getAll();
    LevelDTO getLevelById(long id);
    long getLevelByLevelCode(String levelCode);
}
