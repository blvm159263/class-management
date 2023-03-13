package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassFilterRequestDTO {
    List<String> locations;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate nowDate;
    List<TimeRangeDTO> TimeRanges;
    List<String> statuses;
    List<String> attendees;
    String fsu;
    String trainer;
}
