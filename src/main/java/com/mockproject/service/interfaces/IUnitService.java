package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;
import com.mockproject.entity.User;

import java.io.IOException;
import java.util.List;

import com.mockproject.dto.UnitDTO;
import com.mockproject.entity.Unit;

import java.util.List;

public interface IUnitService {

    List<UnitDTO> listBySessionId(Long sid);

    List<UnitDTO> getAllUnitBySessionId(long sessionId, boolean status);

    boolean createUnit(long sessionId, List<UnitDTO> listUnit, User user);

    boolean createUnit(long sessionId, UnitDTO unitDTO, User user);

    Unit editUnit(UnitDTO unitDTO, boolean status) throws IOException;

    boolean deleteUnit(long unitId, boolean status);

    boolean deleteUnits(long sessionId, boolean status);

}
