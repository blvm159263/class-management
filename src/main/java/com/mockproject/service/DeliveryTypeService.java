package com.mockproject.service;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.DeliveryType;
import com.mockproject.mapper.DeliveryTypeMapper;
import com.mockproject.repository.DeliveryTypeRepository;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import com.mockproject.utils.ListUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryTypeService implements IDeliveryTypeService {

    private final DeliveryTypeRepository deliveryTypeRepository;

    @Override
    public List<DeliveryTypeDTO> getDeliveryTypes(boolean status) {
        Optional<List<DeliveryType>> deliveryTypeList = deliveryTypeRepository.findByStatus(status);
        ListUtils.checkList(deliveryTypeList);
        List<DeliveryTypeDTO> deliveryTypeDTOList = new ArrayList<>();
        for (DeliveryType d: deliveryTypeList.get()){
            deliveryTypeDTOList.add(DeliveryTypeMapper.INSTANCE.toDTO(d));
        }
        return deliveryTypeDTOList;
    }

    @Override
    public DeliveryTypeDTO getDeliveryType(long deliveryId, boolean status){
        Optional<DeliveryType> deliveryType = deliveryTypeRepository.findByIdAndStatus(deliveryId,status);
        deliveryType.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
        DeliveryTypeDTO deliveryTypeDTO = DeliveryTypeMapper.INSTANCE.toDTO(deliveryType.get());
        return deliveryTypeDTO;
    }

}
