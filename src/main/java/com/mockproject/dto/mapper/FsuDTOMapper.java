package com.mockproject.dto.mapper;


import com.mockproject.dto.FsuDTO;
import com.mockproject.entity.Fsu;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
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
