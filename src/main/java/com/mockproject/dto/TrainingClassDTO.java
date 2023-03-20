package com.mockproject.dto;

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
    private int period;
    private boolean status;
    private Long locationId;
    private String locationName;
    private Long attendeeId;
    private String attendeeName;
    private Long trainingProgramId;
    private String trainingProgramName;
    private long fsuId;
    private String fsuName;
    private long contactId;
    private String contactName;
    private long creatorId;
    private String creatorName;
    private long lastModifierId;
    private String lastModifierName;
    private long reviewerId;
    private String reviewerName;
    private long approverId;
    private String approverName;
}
