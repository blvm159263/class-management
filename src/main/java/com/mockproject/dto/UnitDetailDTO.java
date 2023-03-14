package com.mockproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDetailDTO implements Serializable {
    private long id;
    private String title;
    @Min(0)
    private BigDecimal duration;
    @NotNull
    private boolean type;
    @NotNull
    private boolean status;
    private long unitId;
    private long deliveryTypeId;
    private long outputStandardId;
}
