package com.mockproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassDTO implements Serializable {
    @Schema(hidden = true)
    private Long id;
    private String className;
    private String classCode;
    private LocalDate startDate;
    private Time startTime;
    private Time endTime;
    private BigDecimal hour;
    private int day;
    private int planned;
    private int accepted;
    private int actual;
    private String state;
    private LocalDate dateCreated;
    private LocalDate dateReviewed;
    private LocalDate dateApproved;
    private LocalDate lastDateModified;
    private boolean status;
    private Long attendeeId;
    private Long trainingProgramId;
    private Long fsuId;
    private Long contactId;
    private Long creatorId;
    private Long lastModifierId;
    private Long reviewerId;
    private Long approverId;
}
