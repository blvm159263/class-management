package com.mockproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitDetailDTO implements Serializable {
    private Long id;
    private String title;
    private BigDecimal duration;
    private boolean type;
    private boolean status;
    private Long unitId;
    private String unitTitle;
    private Long deliveryTypeId;
    private String deliveryTypeName;
    private Long outputStandardId;
    private String outputStandardName;
    private String outputStandardCode;
    private List<TrainingMaterialDTO> trainingMaterialDTOList;
}
