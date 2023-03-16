package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;

import java.util.List;

public interface IUnitService {

    List<UnitDTO> listBySessionId(Long sid);

    List<UnitDTO> getAllUnitsForADateByTrainingClassId(long id, int dayNth);

    List<Unit> getListUnitsByTrainingClassId(long id);

    List<Unit> getListUnitsInASessionByTrainingClassId(long id, int dayNth);
}
