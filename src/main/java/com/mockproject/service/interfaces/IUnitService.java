package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDTO;

import java.util.List;

public interface IUnitService {
    List<UnitDTO> getAllUnitsForTheDate(long id, int day);

    List<UnitDTO> getAllUnitByClassId(long id);

}
