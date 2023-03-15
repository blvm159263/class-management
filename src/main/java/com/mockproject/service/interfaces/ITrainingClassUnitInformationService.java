package com.mockproject.service.interfaces;

import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.dto.TrainingClassUnitInformationDTO;

import java.util.List;

public interface ITrainingClassUnitInformationService {

    boolean saveList(List<TrainingClassUnitInformationDTO> listDto);

    public List<TrainingClassUnitInformation> findAllByTrainingClassId(Long id);
}
