package com.mockproject.service.interfaces;

import com.mockproject.dto.SessionDTO;
import com.mockproject.dto.UnitDTO;

import java.util.List;

public interface IUnitService {
    List<UnitDTO> getListUnit(List<SessionDTO> session);
}
