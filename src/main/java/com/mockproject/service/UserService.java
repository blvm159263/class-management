package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;

    private final UnitService unitService;

    @Override
    public List<UserDTO> getTrainerByClassId(long id) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<TrainingClassUnitInformation> classUnitInformations = trainingClass.getListTrainingClassUnitInformations()
                .stream()
                .filter(TrainingClassUnitInformation::isStatus)
                .toList();
        List<User> trainer = classUnitInformations.stream()
                .map(TrainingClassUnitInformation :: getTrainer)
                .filter(User::isStatus)
                .distinct().toList();
        return trainer.stream().map(UserMapper.INSTANCE :: toDTO).toList();
    }

    @Override
    public List<UserDTO> getTrainerOnThisDayById(long id, int day) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<Unit> unitListFromSession = unitService.getListUnitsFromSession(id, day);
        List<TrainingClassUnitInformation> list = unitListFromSession.stream()
                .map(p -> trainingClassUnitInformationRepository
                        .findByUnitAndTrainingClassAndStatus(p, trainingClass, true).orElseThrow())
                .toList();
        List<User> trainers = list.stream()
                .map(TrainingClassUnitInformation::getTrainer)
                .filter(User::isStatus)
                .toList();
        return trainers.stream().map(UserMapper.INSTANCE::toDTO).toList();

    }

    @Override
    public UserDTO getCreatorByClassId(long id) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        return UserMapper.INSTANCE.toDTO(trainingClass.getCreator());
    }

    @Override
    public UserDTO getReviewerByClassId(long id) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        return UserMapper.INSTANCE.toDTO(trainingClass.getReviewer());
    }

    @Override
    public UserDTO getApproverByClassId(long id) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        return UserMapper.INSTANCE.toDTO(trainingClass.getApprover());
    }
}
