package com.mockproject.service;

import com.mockproject.dto.TrainingMaterialDTO;
import com.mockproject.dto.UnitDetailDTO;
import com.mockproject.entity.TrainingMaterial;
import com.mockproject.mapper.TrainingMaterialMapper;
import com.mockproject.repository.TrainingMaterialRepository;
import com.mockproject.service.interfaces.ITrainingMaterialService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingMaterialService implements ITrainingMaterialService {
    private final TrainingMaterialRepository trainingMaterialRepository;
    @Override
    public  List<TrainingMaterialDTO> getListTrainingMaterial(List<UnitDetailDTO> listUnitDetail){
        List<TrainingMaterialDTO> listTrainingMaterial = new ArrayList<>();
        for(UnitDetailDTO u : listUnitDetail){
            listTrainingMaterial.addAll(getListTrainingMaterialByUnitDetailId(u.getId()));
        }
        return listTrainingMaterial;
    }
    public List<TrainingMaterialDTO> getListTrainingMaterialByUnitDetailId(Long id){
        return trainingMaterialRepository.getListTrainingMaterialByUnitDetailId(id).stream().map(TrainingMaterialMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

}
