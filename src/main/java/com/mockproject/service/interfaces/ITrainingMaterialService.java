package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.dto.UnitDetailDTO;

import java.util.List;

public interface ITrainingMaterialService {
    List<TrainingMaterialDTO> getListTrainingMaterial(List<UnitDetailDTO> listUnitDetail);
}
