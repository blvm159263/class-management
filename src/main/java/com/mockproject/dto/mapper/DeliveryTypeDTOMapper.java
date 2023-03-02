package com.mockproject.dto.mapper;


import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.DeliveryType;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DeliveryTypeDTOMapper implements Function<DeliveryType, DeliveryTypeDTO> {

    @Override
    public DeliveryTypeDTO apply(DeliveryType deliveryType) {
        return new DeliveryTypeDTO(
                deliveryType.getId(),
                deliveryType.getTypeName(),
                deliveryType.isStatus()
        );
    }
}
