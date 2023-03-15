package com.mockproject.service.interfaces;

import com.mockproject.dto.DeliveryTypeDTO;

import java.util.List;

import com.mockproject.dto.DeliveryTypeDTO;

public interface IDeliveryTypeService {

    DeliveryTypeDTO getByIdTrue(Long id);

    List<DeliveryTypeDTO> getDeliveryTypes(boolean status);

    DeliveryTypeDTO getDeliveryType(long deliveryId, boolean status);
}
