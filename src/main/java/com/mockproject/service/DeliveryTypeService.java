package com.mockproject.service;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.DeliveryType;
import com.mockproject.entity.Unit;
import com.mockproject.entity.UnitDetail;
import com.mockproject.mapper.DeliveryTypeMapper;
import com.mockproject.repository.DeliveryTypeRepository;
import com.mockproject.repository.UnitDetailRepository;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import com.mockproject.service.interfaces.IUnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryTypeService implements IDeliveryTypeService {

    private final DeliveryTypeRepository repository;
    private final UnitDetailRepository unitDetailRepository;


    private final IUnitService service;

    @Override
    public DeliveryTypeDTO getByIdTrue(Long id) {
        UnitDetail unitDetail = new UnitDetail();
        unitDetail.setId(id);
        return DeliveryTypeMapper.INSTANCE.toDTO(repository.findByIdAndStatus(id,true).orElseThrow());
    }

    @Override
    public List<DeliveryTypeDTO> getAllDeliveryTypesByTrainingClassId(long id) {
        List<Unit> units = service.getListUnitsByTrainingClassId(id);
        List<UnitDetail> list = unitDetailRepository.findByUnitInAndStatus(units, true).orElseThrow();
        List<DeliveryType> deliveryTypes = list.stream().map(p-> repository.findByIdAndStatus(p.getDeliveryType().getId(), true).orElseThrow()).distinct().toList();
        return deliveryTypes.stream().map(DeliveryTypeMapper.INSTANCE::toDTO).toList();
    }
}
