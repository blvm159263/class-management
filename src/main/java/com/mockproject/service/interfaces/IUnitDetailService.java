package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDetailDTO;

import java.util.List;

public interface IUnitDetailService {

    List<UnitDetailDTO> listByUnitIdTrue(Long id);
}
