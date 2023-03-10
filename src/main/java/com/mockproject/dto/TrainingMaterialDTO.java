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
    private Long id;
    private LocalDate uploadDate;
    private byte[] data;
    private String name;
    private String type;
    private BigDecimal size;
    private boolean status;
    private Long unitDetailId;
    private Long userId;
}
