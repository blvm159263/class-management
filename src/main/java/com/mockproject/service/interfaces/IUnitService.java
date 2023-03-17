package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import com.mockproject.entity.User;

import java.io.IOException;
import java.util.List;

public interface IUnitService {

    List<UnitDTO> listBySessionId(Long sid);

    List<UnitDTO> getAllUnitsForADateByTrainingClassId(long id, int dayNth);

    List<Unit> getListUnitsByTrainingClassId(long id);

    List<Unit> getListUnitsInASessionByTrainingClassId(long id, int dayNth);

    List<Unit> getUnitBySessionId(long idSession);

    List<UnitDTO> getAllUnitBySessionId(Long sessionId, boolean status);

    boolean createUnit(Long sessionId, List<UnitDTO> listUnit, User user);

    boolean createUnit(Long sessionId, UnitDTO unitDTO, User user);

    Unit editUnit(UnitDTO unitDTO, boolean status) throws IOException;

    boolean deleteUnit(Long unitId, boolean status);

    boolean deleteUnits(Long sessionId, boolean status);

}
