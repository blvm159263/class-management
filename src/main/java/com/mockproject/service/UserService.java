package com.mockproject.service;

import com.mockproject.dto.UserDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.UserMapper;

import com.mockproject.repository.TrainingClassAdminRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.IUnitService;
import com.mockproject.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private static final Long SUPER_ADMIN = 1L;
    private static final Long CLASS_ADMIN = 2L;
    private static final Long TRAINER = 3L;
    private static final Long STUDENT = 4L;

    private final UserRepository repository;
    private final TrainingClassRepository trainingClassRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    private final TrainingClassAdminRepository trainingClassAdminRepository;

    private final IUnitService unitService;


    @Override
    public List<UserDTO> listClassAdminTrue() {
        Role role = new Role();
        role.setId(CLASS_ADMIN);
        return repository.findByRoleAndStatus(role,true).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> listTrainerTrue() {
        Role role = new Role();
        role.setId(TRAINER);
        return repository.findByRoleAndStatus(role,true).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserMapper.INSTANCE.toDTO(repository.findById(id).orElse(null));
    }

    @Override
    public List<UserDTO> getAllUser(boolean status) {
        return repository.findAllByStatus(status).stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllTrainersByTrainingClassId(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<TrainingClassUnitInformation> list = trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc, true).orElseThrow();
        List<User> listUser = list.stream().map(p -> repository.findByIdAndStatus(p.getTrainer().getId(), true).orElseThrow()).distinct().toList();
        return listUser.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<UserDTO> getAllAdminsByTrainingClassId(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<TrainingClassAdmin> list = trainingClassAdminRepository.findByTrainingClassAndStatus(tc, true);
        List<User> admins = list.stream().map(p -> repository.findByIdAndStatus(p.getAdmin().getId(), true).orElseThrow()).toList();
        return admins.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public UserDTO getCreatorByTrainingClassId(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        User user = repository.findByIdAndStatus(tc.getCreator().getId(), true).orElseThrow();
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllTrainersForADateByTrainingClassId(long id, int dayNth) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<Unit> units = unitService.getListUnitsInASessionByTrainingClassId(id, dayNth);
        List<TrainingClassUnitInformation> list = units.stream().map(p-> trainingClassUnitInformationRepository.findByUnitAndTrainingClassAndStatus(p, tc, true).orElseThrow()).toList();
        List<User> trainers = list.stream().map(p-> repository.findByIdAndStatus(p.getTrainer().getId(), true).orElseThrow()).toList();
        return trainers.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }
}
