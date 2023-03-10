package com.mockproject.service;

import com.mockproject.dto.TrainingClassAdminDTO;
import com.mockproject.dto.UserDTO;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingClassAdmin;
import com.mockproject.entity.User;
import com.mockproject.mapper.TrainingClassAdminMapper;
import com.mockproject.mapper.UserMapper;
import com.mockproject.repository.TrainingClassAdminRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.UserRepository;
import com.mockproject.service.interfaces.ITrainingClassAdminService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassAdminService implements ITrainingClassAdminService {

    private final TrainingClassAdminRepository repository;

    private final TrainingClassRepository trainingClassRepository;

    private final TrainingClassAdminRepository trainingClassAdminRepository;


    @Override
    public List<UserDTO> getAdminByClassCode(String code) {
        TrainingClass tc = trainingClassRepository.findByClassCodeAndStatus(code, true).get(0);
        List<TrainingClassAdmin> trainingClassAdmins = tc.getListTrainingClassAdmins()
                .stream()
                .filter(TrainingClassAdmin :: isStatus)
                .toList();
        List<User> userList = trainingClassAdmins.stream().map(TrainingClassAdmin::getAdmin)
                .filter(User::isStatus)
                .distinct()
                .toList();
        return userList.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

}
