package com.mockproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeDTO {

    @JsonFormat(pattern = "HH:mm:ss")
    private Time from;
    @JsonFormat(pattern = "HH:mm:ss")
    private Time to;
}
