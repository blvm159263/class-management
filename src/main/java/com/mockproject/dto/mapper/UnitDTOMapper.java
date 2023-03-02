package com.mockproject.dto.mapper;


import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UnitDTOMapper implements Function<Unit, UnitDTO> {

    @Override
    public UnitDTO apply(Unit unit) {
        return new UnitDTO(
                unit.getId(),
                unit.getUnitTitle(),
                unit.getUnitNumber(),
                unit.getDuration(),
                unit.isStatus(),
                unit.getSession().getId()
        );
    }
}