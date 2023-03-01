package com.mockproject.dto.mapper;


import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.Fsu;

import java.util.function.Function;

public class FsuDTOMapper implements Function<Fsu, FsuDTO> {


    @Override
    public FsuDTO apply(Fsu fsu) {
        return new FsuDTO(
                fsu.getId(),
                fsu.getFsuName(),
                fsu.getDescription(),
                fsu.isStatus()
        );
    }
}
