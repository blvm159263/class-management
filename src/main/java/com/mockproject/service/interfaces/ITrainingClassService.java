package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassDTO;

public interface ITrainingClassService {

    Long create(TrainingClassDTO trainingClassDTO);

    boolean deleteTrainingClass(Long id);

    boolean duplicateClass(Long id);

}
