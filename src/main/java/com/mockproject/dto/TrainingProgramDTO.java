package com.mockproject.dto;

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
public class TrainingProgramDTO implements Serializable {
    private Long id;
    private int programId;
    private String name;
    private LocalDate dateCreated;
    private LocalDate lastDateModified;
    private BigDecimal hour;
    private int day;
    private boolean status;
    private Long creatorId;
    private Long lastModifierId;
    private List<Long> syllabusIdList;
}
