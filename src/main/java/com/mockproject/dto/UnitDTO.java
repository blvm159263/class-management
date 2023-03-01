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
public class UnitDTO implements Serializable {
    private long id;
    private String unitTitle;
    private int unitNumber;
    private BigDecimal duration;
    private boolean status;
    private long sessionId;
}
