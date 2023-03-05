package com.mockproject.dto.mapper;


import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
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
                syllabus.isState(),
                syllabus.isStatus(),
                syllabus.getCreator().getId(),
                syllabus.getLastModifier().getId(),
                new SessionDTOMapper().apply(syllabus.getListSessions())
        );
    }
}
