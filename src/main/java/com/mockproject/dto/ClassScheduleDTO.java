package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleDTO implements Serializable {
    private long id;
    private LocalDate date;
    private boolean status;
    private long trainingClassId;
}
