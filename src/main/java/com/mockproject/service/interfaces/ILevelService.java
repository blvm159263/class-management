package com.mockproject.service.interfaces;

import com.mockproject.entity.Level;

import java.util.Optional;

public interface ILevelService {
    public String getLevelCodeById(long id);
    public long getLevelByLevelCode(String levelCode);
}
