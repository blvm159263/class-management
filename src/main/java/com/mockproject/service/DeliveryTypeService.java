package com.mockproject.service;

import com.mockproject.dto.DeliveryTypeDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.DeliveryTypeMapper;
import com.mockproject.repository.DeliveryTypeRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IDeliveryTypeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryTypeService implements IDeliveryTypeService {

    private final DeliveryTypeRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public List<DeliveryTypeDTO> getListDeliveryTpeByCLassCode(String code, boolean status) {
        TrainingClass trainingClass = trainingClassRepository.findByClassCodeAndStatus(code, status).get(0);
        List<TrainingClassUnitInformation> trainingClassUnitInformations = trainingClass.getListTrainingClassUnitInformations();
        Unit unit = trainingClassUnitInformations.get(0).getUnit();
        List<UnitDetail> unitDetails = unit.getListUnitDetail();
        List<DeliveryType> deliveryType = unitDetails.stream().map(UnitDetail::getDeliveryType).distinct().toList();
        return deliveryType.stream().map(DeliveryTypeMapper.INSTANCE::toDTO).toList();
    }
}
