package com.mockproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private byte[] data;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @Min(0)
    private BigDecimal size;
    @NotNull
    private boolean status;
    private long unitDetailId;
    private long userId;
}
