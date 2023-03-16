package com.mockproject.service.interfaces;

import com.mockproject.dto.DeliveryTypeDTO;

import java.util.List;

public interface IDeliveryTypeService {

    DeliveryTypeDTO getByIdTrue(Long id);

    List<DeliveryTypeDTO> getDeliveryTypes(boolean status);

    DeliveryTypeDTO getDeliveryType(long deliveryId, boolean status);
}
