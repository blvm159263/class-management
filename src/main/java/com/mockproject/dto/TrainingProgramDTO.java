package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingProgramDTO implements Serializable {
    private Long id;
<<<<<<< HEAD
=======
    private int programId;
>>>>>>> 65706b88e6cc434b660da1678ee62c26aee583b1
    private String name;
    private LocalDate dateCreated;
    private LocalDate lastDateModified;
    private BigDecimal hour;
    private int day;
    private boolean status;
    private Long creatorId;
    private Long lastModifierId;
}
