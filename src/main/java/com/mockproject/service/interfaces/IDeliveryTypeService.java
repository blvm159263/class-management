package com.mockproject.service.interfaces;

import com.mockproject.dto.DeliveryTypeDTO;

import java.util.List;

public interface IDeliveryTypeService {

    List<DeliveryTypeDTO> getDeliveryTypes(boolean status);
    DeliveryTypeDTO getDeliveryType(long deliveryId, boolean status);
}
