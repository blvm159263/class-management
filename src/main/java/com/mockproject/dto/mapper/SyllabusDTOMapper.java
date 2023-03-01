package com.mockproject.dto.mapper;


import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;

import java.util.function.Function;

public class SyllabusDTOMapper implements Function<Syllabus, SyllabusDTO> {

    @Override
    public SyllabusDTO apply(Syllabus syllabus) {
        return new SyllabusDTO(
                syllabus.getId(),
                syllabus.getName(),
                syllabus.getCode(),
                syllabus.getVersion(),
                syllabus.getLevel(),
                syllabus.getAttendee(),
                syllabus.getTechnicalRequirements(),
                syllabus.getCourseObjectives(),
                syllabus.getDateCreated(),
                syllabus.getLastDateModified(),
                syllabus.getQuiz(),
                syllabus.getAssignment(),
                syllabus.getFinalExam(),
                syllabus.getFinalTheory(),
                syllabus.getFinalPractice(),
                syllabus.getGpa(),
                syllabus.getTrainingDes(),
                syllabus.getReTestDes(),
                syllabus.getMarkingDes(),
                syllabus.getWaiverCriteriaDes(),
                syllabus.getOtherDes(),
                syllabus.isStatus(),
                syllabus.getCreator().getId(),
                syllabus.getLastModifier().getId()
        );
    }
}
