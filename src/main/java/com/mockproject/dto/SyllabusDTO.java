package com.mockproject.dto;

import com.mockproject.entity.Session;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String version;

    @NotBlank
    private String level;

    @NotBlank
    private int attendee;

    @NotBlank
    private String technicalRequirements;

    @NotBlank
    private String courseObjectives;

    @NotBlank
    private LocalDate dateCreated;

    @NotBlank
    private LocalDate lastDateModified;

    @NotBlank
    private BigDecimal quiz;

    @NotBlank
    private BigDecimal assignment;

    @NotBlank
    private BigDecimal finalExam;

    @NotBlank
    private BigDecimal finalTheory;

    @NotBlank
    private BigDecimal finalPractice;

    @NotBlank
    private BigDecimal gpa;

    @NotBlank
    private String trainingDes;

    @NotBlank
    private String reTestDes;

    @NotBlank
    private String markingDes;

    @NotBlank
    private String waiverCriteriaDes;

    @NotBlank
    private String otherDes;

    @NotBlank
    private boolean state;

    @NotBlank
    private boolean status;

    @NotBlank
    private long creatorId;

    @NotBlank
    private long lastModifierId;
}
