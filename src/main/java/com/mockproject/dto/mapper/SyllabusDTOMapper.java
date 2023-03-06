package com.mockproject.dto.mapper;


import com.mockproject.dto.SyllabusDTO;
import com.mockproject.entity.Syllabus;
import com.mockproject.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                syllabus.getLastModifier().getId()
        );
    }

    public List<SyllabusDTO> toDTOs(List<Syllabus> list){
        List<SyllabusDTO> syllabusDTOList = new ArrayList<>();

        list.forEach((i) -> syllabusDTOList.add(apply(i)));

        return syllabusDTOList;
    }

    public Syllabus toEntity(SyllabusDTO dto, User user) {
        Syllabus entity = Syllabus.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .version(dto.getVersion())
                .level(dto.getLevel())
                .attendee(dto.getAttendee())
                .technicalRequirements(dto.getTechnicalRequirements())
                .courseObjectives(dto.getCourseObjectives())
                .dateCreated(dto.getDateCreated())
                .lastDateModified(dto.getLastDateModified())
                .quiz(dto.getQuiz())
                .assignment(dto.getAssignment())
                .finalExam(dto.getFinalExam())
                .finalTheory(dto.getFinalTheory())
                .finalPractice(dto.getFinalPractice())
                .gpa(dto.getGpa())
                .trainingDes(dto.getTrainingDes())
                .reTestDes(dto.getReTestDes())
                .markingDes(dto.getMarkingDes())
                .waiverCriteriaDes(dto.getWaiverCriteriaDes())
                .otherDes(dto.getOtherDes())
                .state(dto.isState())
                .status(dto.isStatus())
                .creator(user)
                .lastModifier(user)
                .build();
        return entity;
    }
}
