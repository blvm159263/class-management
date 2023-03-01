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
public class TrainingClassAdminDTO implements Serializable {
    private long id;
    private boolean status;
    private UserDTO admin;
    private TrainingClassDTO trainingClassDTO;
}
