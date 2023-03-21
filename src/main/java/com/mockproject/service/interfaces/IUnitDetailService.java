package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDTO;
import com.mockproject.dto.UnitDetailDTO;

import java.util.List;

public interface IUnitDetailService {
    List<UnitDetailDTO> getListUnitDetail(List<UnitDTO> listUnit);
}
