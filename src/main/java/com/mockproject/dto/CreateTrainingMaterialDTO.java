package com.mockproject.dto;

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
    public String data;
    public long unitDetailID;

    public String name;

    public BigDecimal size;

    public String type;

}
