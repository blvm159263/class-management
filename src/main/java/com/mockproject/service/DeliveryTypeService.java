package com.mockproject.service;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.UnitDetail;
import com.mockproject.mapper.DeliveryTypeMapper;
import com.mockproject.repository.DeliveryTypeRepository;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryTypeService implements IDeliveryTypeService {

    private final DeliveryTypeRepository repository;

    @Override
    public DeliveryTypeDTO getByIdTrue(Long id) {
        UnitDetail unitDetail = new UnitDetail();
        unitDetail.setId(id);
        return DeliveryTypeMapper.INSTANCE.toDTO(repository.findByIdAndStatus(id,true).orElseThrow());
    }
}
