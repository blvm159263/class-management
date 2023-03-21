package com.mockproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
//<<<<<<< HEAD
//=======
    @Schema(hidden = true)
//>>>>>>> ed0aa085b95a13c82c9adb2ccaee04db52e424cf
    private Long id;
    private boolean status;
    private Long trainerId;
    private Long unitId;
    private Long trainingClassId;
    private Long towerId;
}
