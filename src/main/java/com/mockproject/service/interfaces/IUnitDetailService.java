package com.mockproject.service.interfaces;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.UnitDetail;
import com.mockproject.entity.User;

import java.io.IOException;
import java.util.List;

public interface IUnitDetailService {

    List<UnitDetailDTO> listByUnitIdTrue(Long id);

    List<UnitDetail> getUnitDetailByUnitId(long idUnit);

    List<UnitDetailDTO> getAllUnitDetailByUnitId(long unitId, boolean status);

    boolean createUnitDetail(long unitId, List<UnitDetailDTO> listUnitDetail, User user);

    boolean createUnitDetail(long unitId, UnitDetailDTO unitDetailDTO, User user);

    UnitDetail getUnitDetailById(long id, boolean status);

    UnitDetail editUnitDetail(UnitDetailDTO unitDetailDTO, boolean status) throws IOException;

    boolean deleteUnitDetail(long unitDetailId, boolean status);

    boolean deleteUnitDetails(long unitId, boolean status);

    boolean toggleUnitDetailType(long unitDetailId, boolean status);

}
