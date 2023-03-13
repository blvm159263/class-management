package com.mockproject.service.interfaces;

import com.mockproject.dto.LevelDTO;
import com.mockproject.entity.Level;

import java.util.Optional;

public interface ILevelService {
    LevelDTO getLevelById(long id);
    long getLevelByLevelCode(String levelCode);
}
