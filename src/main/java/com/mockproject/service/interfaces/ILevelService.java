package com.mockproject.service.interfaces;

import com.mockproject.dto.LevelDTO;

public interface ILevelService {

    LevelDTO getLevelById(long id);

    long getLevelByLevelCode(String levelCode);
}
