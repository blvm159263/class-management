package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassUnitInformation;
import com.mockproject.entity.User;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    @Override
    public List<UserDTO> getTrainerByClassCode(String code) {
        TrainingClass trainingClass = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
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
}
