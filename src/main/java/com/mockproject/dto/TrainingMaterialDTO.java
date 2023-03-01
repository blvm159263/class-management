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
public class TrainingMaterialDTO implements Serializable {
    private long id;
    private LocalDate uploadDate;
    private String data;
    private String name;
    private String type;
    private BigDecimal size;
    private boolean status;
    private long unitDetailId;
    private long userId;
}
