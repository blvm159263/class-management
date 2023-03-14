package com.mockproject.service;

import com.mockproject.dto.*;
import com.mockproject.entity.*;
import com.mockproject.mapper.*;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainingClassService implements ITrainingClassService {

    private final TrainingClassRepository trainingClassRepository;
    private final UserRepository userRepository;
    private final TowerRepository towerRepository;
    private final TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final TrainingClassAdminRepository trainingClassAdminRepository;
    private final SessionRepository sessionRepository;
    private final UnitRepository unitRepository;
    private final UnitDetailRepository unitDetailRepository;
    private final DeliveryTypeRepository deliveryTypeRepository;


    @Override
    public TrainingClassDTO getAllDetails(long id) {
        TrainingClass details = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        return TrainingClassMapper.INSTANCE.toDTO(details);
    }

    @Override
    public List<UserDTO> getAllTrainers(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<TrainingClassUnitInformation> list = trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc, true).orElseThrow();
        List<User> listUser = list.stream().map(p -> userRepository.findByIdAndStatus(p.getTrainer().getId(), true).orElseThrow()).distinct().toList();
        return listUser.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<TowerDTO> getAllTowers(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<TrainingClassUnitInformation> list = trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc, true).orElseThrow();
        List<Tower> listTower = list.stream().map(p -> towerRepository.findByIdAndStatus(p.getTower().getId(), true).orElseThrow()).distinct().toList();
        return listTower.stream().map(TowerMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public AttendeeDTO getAttendee(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        if (tc.getAttendee().isStatus()){
            return AttendeeMapper.INSTANCE.toDTO(tc.getAttendee());
        }
        return null;
    }

    @Override
    public List<ClassScheduleDTO> getClassSchedule(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<ClassSchedule> schedules = classScheduleRepository.findByTrainingClassAndStatusOrderByDateAsc(tc, true).orElseThrow();
        return schedules.stream().map(ClassScheduleMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<UserDTO> getAllAdmins(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<TrainingClassAdmin> list = trainingClassAdminRepository.findByTrainingClassAndStatus(tc, true);
        List<User> admins = list.stream().map(p -> userRepository.findByIdAndStatus(p.getAdmin().getId(), true).orElseThrow()).toList();
        return admins.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public FsuDTO getFsu(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        if(tc.getFsu().isStatus()){
            return FsuMapper.INSTANCE.toDTO(tc.getFsu());
        }
        return null;
    }

    @Override
    public ContactDTO getContact(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        if(tc.getContact().isStatus()){
            return ContactMapper.INSTANCE.toDTO(tc.getContact());
        }
        return null;
    }

    @Override
    public UserDTO getCreator(long id) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        User user = userRepository.findByIdAndStatus(tc.getCreator().getId(), true).orElseThrow();
        return UserMapper.INSTANCE.toDTO(user);
    }


    @Override
    public List<UnitDTO> getAllUnitsForADate(long id, int dayNth) {
        List<Unit> units = getListUnitsInASession(id, dayNth);
        return units.stream().map(UnitMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<UserDTO> getAllTrainersForADate(long id, int dayNth) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<Unit> units = getListUnitsInASession(id, dayNth);
        List<TrainingClassUnitInformation> list = units.stream().map(p-> trainingClassUnitInformationRepository.findByUnitAndTrainingClassAndStatus(p, tc, true).orElseThrow()).toList();
        List<User> trainers = list.stream().map(p-> userRepository.findByIdAndStatus(p.getTrainer().getId(), true).orElseThrow()).toList();
        return trainers.stream().map(UserMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<DeliveryTypeDTO> getAllDeliveryTypes(long id) {
        List<Unit> units = getListUnits(id);
        List<UnitDetail> list = unitDetailRepository.findByUnitInAndStatus(units, true).orElseThrow();
        List<DeliveryType> deliveryTypes = list.stream().map(p-> deliveryTypeRepository.findByIdAndStatus(p.getDeliveryType().getId(), true).orElseThrow()).distinct().toList();
        return deliveryTypes.stream().map(DeliveryTypeMapper.INSTANCE::toDTO).toList();
    }

    @Override
    public List<TowerDTO> getAllTowersForADate(long id, int dayNth) {
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        List<Unit> units = getListUnitsInASession(id, dayNth);
        List<TrainingClassUnitInformation> list = units.stream().map(p-> trainingClassUnitInformationRepository.findByUnitAndTrainingClassAndStatus(p, tc, true).orElseThrow()).toList();
        List<Tower> towers = list.stream().map(p-> towerRepository.findByIdAndStatus(p.getTower().getId(), true).orElseThrow()).distinct().toList();
        return towers.stream().map(TowerMapper.INSTANCE::toDTO).toList();
    }


    // get all units from a class
    private List<Unit> getListUnits(long id){
        // Get Class
        TrainingClass tc = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();

        // Get all units
        List<TrainingClassUnitInformation> list = trainingClassUnitInformationRepository.findByTrainingClassAndStatus(tc, true).orElseThrow();
        return list.stream().map(p-> unitRepository.findByIdAndStatus(p.getUnit().getId(), true).orElseThrow()).toList();
    }


    // Get a session from a date
    private Session getSession(long id, int dayNth){
        // Get all sessions
        List<Session> sessions = getListUnits(id).stream().map(p-> sessionRepository.findByIdAndStatus(p.getSession().getId(), true).orElseThrow()).toList();
        return sessions.get(dayNth);
    }


    // Get list units from a session
    private List<Unit> getListUnitsInASession(long id, int dayNth){
        Session session = getSession(id, dayNth);
        return unitRepository.findBySessionAndStatusOrderByUnitNumber(session, true).orElseThrow();
    }
}
