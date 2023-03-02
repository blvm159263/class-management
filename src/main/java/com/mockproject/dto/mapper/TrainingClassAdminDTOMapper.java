package com.mockproject.dto.mapper;


import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.entity.TrainingClassAdmin;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainingClassAdminDTOMapper implements Function<TrainingClassAdmin, TrainingClassAdminDTO> {

    @Override
    public TrainingClassAdminDTO apply(TrainingClassAdmin trainingClassAdmin) {
        return new TrainingClassAdminDTO(
                trainingClassAdmin.getId(),
                trainingClassAdmin.isStatus(),
                trainingClassAdmin.getAdmin().getId(),
                trainingClassAdmin.getTrainingClass().getId()
        );
    }
}
