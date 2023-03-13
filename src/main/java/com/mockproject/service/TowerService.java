package com.mockproject.service;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Tower;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.mapper.TowerMapper;
import com.mockproject.repository.TowerRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.ITowerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TowerService implements ITowerService {

    private final TowerRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public List<TowerDTO> getTowerByClassCode(String code) {
        TrainingClass tc = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        List<TrainingClassUnitInformation> classUnitInformations = tc.getListTrainingClassUnitInformations()
                .stream()
                .filter(TrainingClassUnitInformation::isStatus)
                .toList();
        List<Tower> towers = classUnitInformations.stream()
                .map(TrainingClassUnitInformation :: getTower)
                .filter(Tower::isStatus)
                .distinct()
                .toList();
        return towers.stream().map(TowerMapper.INSTANCE::toDTO).toList();
    }
}
