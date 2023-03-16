package com.mockproject.service.interfaces;

import com.mockproject.dto.DeliveryTypeDTO;

import java.util.List;

public interface IDeliveryTypeService {

    DeliveryTypeDTO getByIdTrue(Long id);

    List<DeliveryTypeDTO> getAllDeliveryTypesByTrainingClassId(long id);
}
