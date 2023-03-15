package com.mockproject.service.interfaces;

import com.mockproject.entity.TrainingClassUnitInformation;

import java.util.List;

public interface ITrainingClassUnitInformationService {
    public List<TrainingClassUnitInformation> findAllByTrainingClassId(Long id);
}
