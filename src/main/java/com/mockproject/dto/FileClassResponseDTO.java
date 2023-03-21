package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileClassResponseDTO {

    private String className;

    private LocalDate startDate;

    private Time startTime;

    private Time endTime;

    private BigDecimal hour;

    private int day;

    private int planned;

    private int accepted;

    private int actual;

    private Long locationId;

    private Long fsuId;

    private Long contactId;

    private Long trainingProgramId;

    private Long attendeeId;

    private List<Long> listAdminId;

}
