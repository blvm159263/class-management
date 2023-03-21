package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.UnitDetail;

import java.util.List;

public interface IUnitDetailService {
    UnitDetail get(long id);

    List<UnitDetailDTO> listByUnitIdTrue(Long id);
//>>>>>>> ed0aa085b95a13c82c9adb2ccaee04db52e424cf
}
