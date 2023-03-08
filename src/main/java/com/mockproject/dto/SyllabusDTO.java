package com.mockproject.dto;

import com.mockproject.entity.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusDTO implements Serializable {
    private long id;
    private String name;
    private String code;
    private String version;
    private String level;
    private int attendee;
    private BigDecimal hour;
    private int day;
    private String technicalRequirements;
    private String courseObjectives;
    private LocalDate dateCreated;
    private LocalDate lastDateModified;
    private BigDecimal quiz;
    private BigDecimal assignment;
    private BigDecimal finalExam;
    private BigDecimal finalTheory;
    private BigDecimal finalPractice;
    private BigDecimal gpa;
    private String trainingDes;
    private String reTestDes;
    private String markingDes;
    private String waiverCriteriaDes;
    private String otherDes;
    private boolean state;
    private boolean status;
    private long creatorId;
    private long lastModifierId;
    private List<SessionDTO> sessionDTOS;
}
