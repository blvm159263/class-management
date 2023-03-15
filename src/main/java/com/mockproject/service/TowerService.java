package com.mockproject.service;

import com.mockproject.dto.TowerDTO;
import com.mockproject.entity.Tower;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.Unit;
import com.mockproject.mapper.TowerMapper;
import com.mockproject.repository.TowerRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
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

    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;

    private final UnitService unitService;

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

    @Override
    public List<TowerDTO> getTowerForTheDay(long id, int day) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true);
        List<Unit> unitListFromSession = unitService.getListUnitsFromSession(id, day);
        List<TrainingClassUnitInformation> list = unitListFromSession.stream()
                        .map(p -> trainingClassUnitInformationRepository
                                .findByUnitAndTrainingClassAndStatus(p, trainingClass, true)
                                .orElseThrow()).toList();
        List<Tower> towers = list.stream()
                .map(TrainingClassUnitInformation::getTower)
                .filter(Tower::isStatus)
                .toList();
        return towers.stream().map(TowerMapper.INSTANCE::toDTO).toList();
    }
}
