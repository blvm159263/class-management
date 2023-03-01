package com.mockproject.dto.mapper;


import com.mockproject.dto.LevelDTO;
import com.mockproject.entity.Level;

import java.util.function.Function;

public class LevelDTOMapper implements Function<Level, LevelDTO> {


    @Override
    public LevelDTO apply(Level level) {
        return new LevelDTO(
                level.getId(),
                level.getLevelCode(),
                level.getDescription(),
                level.isStatus()
        );
    }
}
