package com.mockproject.dto;

import com.mockproject.entity.UnitDetail;
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
public class UnitDTO implements Serializable {
    private long id;
    private String unitTitle;
    private int unitNumber;
    private BigDecimal duration;
    private boolean status;
    private long sessionId;
    private List<UnitDetailDTO> unitDetailDTOList;
}
