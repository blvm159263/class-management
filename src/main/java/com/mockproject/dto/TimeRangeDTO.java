package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeDTO {
    private Time from;
    private Time to;
}
