package com.mockproject.service;

import com.mockproject.dto.*;
import com.mockproject.entity.*;
import com.mockproject.mapper.*;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService {

    private final TrainingClassRepository trainingClassRepository;
    private final UserRepository userRepository;
    private final TowerRepository towerRepository;
//    private final AttendeeRepository attendeeRepository;


    @Override
    public TrainingClassDTO getAllDetails(long id) {
        TrainingClass details = trainingClassRepository.findByIdAndStatus(id, true);
        return TrainingClassMapper.INSTANCE.toDTO(details);
    }

//    @Override
//    public List<UserDTO> getAllTrainers(int id) {
//        List<User> trainers = repository.getAllTrainersInfo(id);
//        return trainers.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
//    }

    @Override
    public List<UserDTO> getAllTrainers(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        List<TrainingClassUnitInformation> list = tc.getListTrainingClassUnitInformations()
                .stream()
                .filter(TrainingClassUnitInformation::isStatus)
                .toList();

        List<User> listUser = list
                .stream()
                .map(p -> userRepository.findById(p.getTrainer().getId())
                        .filter(User::isStatus)
                        .orElseThrow())
                .toList();

        return listUser.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<TowerDTO> getAllTowers(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        List<TrainingClassUnitInformation> list = tc.getListTrainingClassUnitInformations()
                .stream()
                .filter(TrainingClassUnitInformation::isStatus)
                .toList();

        List<Tower> listTower = list.stream()
                .map(p -> towerRepository.findById(p.getTower().getId())
                        .filter(Tower::isStatus)
                        .orElseThrow())
                .toList();

        return listTower.stream().map(TowerMapper.INSTANCE::toDTO).toList();
    }

//    @Override
//    public List<TowerDTO> getAllTowers(long id) {
//        List<Tower> list = trainingClassRepository.getAllTowersInfo(id);
//        return list.stream().map(TowerMapper.INSTANCE::toDTO).collect(Collectors.toList());
//    }

    @Override
    public AttendeeDTO getAttendee(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        if (tc.getAttendee().isStatus()){
            return AttendeeMapper.INSTANCE.toDTO(tc.getAttendee());
        }

//        Attendee attendee = attendeeRepository.findById(tc.getAttendee().getId())
//                .filter(Attendee::isStatus)
//                .orElseThrow();

        return null;
    }

    @Override
    public List<ClassScheduleDTO> getClassSchedule(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        List<ClassSchedule> schedules = tc.getListClassSchedules()
                .stream()
                .filter(ClassSchedule::isStatus)
                .toList();

        return schedules.stream().map(ClassScheduleMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<UserDTO> getAllAdmins(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        List<TrainingClassAdmin> list = tc.getListTrainingClassAdmins()
                .stream()
                .filter(TrainingClassAdmin::isStatus)
                .toList();

        List<User> admins = list.stream()
                .map(p -> userRepository.findById(p.getAdmin().getId())
                        .filter(User::isStatus)
                        .orElseThrow())
                .toList();

        return admins.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public FsuDTO getFsu(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        if(tc.getFsu().isStatus()){
            return FsuMapper.INSTANCE.toDTO(tc.getFsu());
        }
        return null;
    }

    @Override
    public ContactDTO getContact(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        if(tc.getContact().isStatus()){
            return ContactMapper.INSTANCE.toDTO(tc.getContact());
        }
        return null;
    }

    @Override
    public UserDTO getCreator(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);
        User user = userRepository.findById(tc.getCreator().getId()).filter(User::isStatus).orElseThrow();
        return UserMapper.INSTANCE.toDTO(user);
    }

    @Override
    public Integer getShortDetails(long id, LocalDate targetDate) {
        // Get class
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true);

        // Get list of days from schedule
        List<ClassSchedule> schedules = tc.getListClassSchedules().stream().filter(ClassSchedule::isStatus).toList();
        List<LocalDate> listOfDates = schedules.stream().map(ClassSchedule::getDate).toList();

        // Count day(s) before the chosen day
        int daysBefore = 0;
        for (LocalDate date : listOfDates){
            if(date.isBefore(targetDate)){
                daysBefore++;
            }
        }

        return daysBefore + 1;
    }

    @Override
    public DeliveryTypeDTO getDeliveryType(long id) {

        return null;
    }


}
