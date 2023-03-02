package com.mockproject.dto.mapper;


import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.TrainingClass;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainingClassDTOMapper implements Function<TrainingClass, TrainingClassDTO> {

    @Override
    public TrainingClassDTO apply(TrainingClass trainingClass) {
        return new TrainingClassDTO(
                trainingClass.getId(),
                trainingClass.getClassName(),
                trainingClass.getClassCode(),
                trainingClass.getStartDate(),
                trainingClass.getStartTime(),
                trainingClass.getEndTime(),
                trainingClass.getHour(),
                trainingClass.getDay(),
                trainingClass.getPlanned(),
                trainingClass.getAccepted(),
                trainingClass.getActual(),
                trainingClass.getState(),
                trainingClass.getDateCreated(),
                trainingClass.getDateReviewed(),
                trainingClass.getDateApproved(),
                trainingClass.getLastDateModified(),
                trainingClass.isStatus(),
                trainingClass.getAttendee().getId(),
                trainingClass.getTrainingProgram().getId(),
                trainingClass.getFsu().getId(),
                trainingClass.getContact().getId(),
                trainingClass.getCreator().getId(),
                trainingClass.getLastModifier().getId(),
                trainingClass.getReviewer().getId(),
                trainingClass.getApprover().getId()
        );
    }
}
