package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingClassUnitInformationDTO implements Serializable {
    private Long id;
    private boolean status;
    private Long trainerId;
    private Long unitId;
    private Long trainingClassId;
    private Long towerId;
}
