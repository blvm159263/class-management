package com.mockproject.dto;

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
    private BigDecimal duration;
    private boolean type;
    private boolean status;
    private UnitDTO unitDTO;
    private DeliveryTypeDTO deliveryTypeDTO;
    private OutputStandardDTO outputStandardDTO;
}
