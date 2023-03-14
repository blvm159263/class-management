package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassUnitInformationDTO;

import java.util.List;

public interface ITrainingClassUnitInformationService {

    boolean saveList(List<TrainingClassUnitInformationDTO> listDto);
}
