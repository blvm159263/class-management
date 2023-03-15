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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    private final UnitService unitService;

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

    @Override
    public UserDTO getTrainerOntheDayById(long id, int day) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true);
//        List<TrainingClassUnitInformation> unitList = unitServic
        return null;
    }

    //    @Override
//    public List<UserDTO> getTrainerById(long id) {
//        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).get(0);
//        List<TrainingClassUnitInformation> trainingClassUnitInfor = trainingClass.getListTrainingClassUnitInformations()
//                .stream()
//                .filter(TrainingClassUnitInformation::isStatus)
//                .toList();
//        List<User> trainer = trainingClassUnitInfor.stream()
//                .map(TrainingClassUnitInformation::getTrainer)
//                .filter(User::isStatus)
//                .distinct().toList();
//        return trainer.stream().map(UserMapper.INSTANCE::toDTO).toList();
//    }

    @Override
    public UserDTO getCreatorByClassCode(String code) {
        TrainingClass trainingClass = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        if(trainingClass.getCreator().isStatus()) {
            return UserMapper.INSTANCE.toDTO(trainingClass.getCreator());
        }
        return null;
    }

    @Override
    public UserDTO getReviewerByClassCode(String code) {
        TrainingClass trainingClass = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        if(trainingClass.getReviewer().isStatus()) {
            return UserMapper.INSTANCE.toDTO(trainingClass.getReviewer());
        }
        return null;
    }

    @Override
    public UserDTO getApproverByClassCode(String code) {
        TrainingClass trainingClass = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        if(trainingClass.getApprover().isStatus()) {
            return UserMapper.INSTANCE.toDTO(trainingClass.getApprover());
        }
        return null;
    }
}
