package com.mockproject.dto.mapper;

import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.UnitDetail;

import java.util.function.Function;

public class UnitDetailDTOMapper implements Function<UnitDetail, UnitDetailDTO> {

    @Override
    public UnitDetailDTO apply(UnitDetail unitDetail) {
        return new UnitDetailDTO(
                unitDetail.getId(),
                unitDetail.getTitle(),
                unitDetail.getDuration(),
                unitDetail.isType(),
                unitDetail.isStatus(),
                unitDetail.getUnit().getId(),
                unitDetail.getDeliveryType().getId(),
                unitDetail.getOutputStandard().getId()
        );
    }
}
