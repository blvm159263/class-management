package com.mockproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class CreateTrainingMaterialDTO {
    @NotNull
    public String data;
    public Long unitDetailID;
    @NotNull
    public String name;
    @Min(0)
    public BigDecimal size;
    @NotNull
    public String type;

}
