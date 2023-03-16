package com.mockproject.service;

import com.mockproject.dto.*;
import com.mockproject.entity.*;
import com.mockproject.mapper.*;
import com.mockproject.repository.*;
import com.mockproject.service.interfaces.ITrainingClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.sql.Time;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

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
    private final LocationRepository locationRepository;
    private final TrainingProgramRepository trainingProgramRepository;

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
    public Long create(TrainingClassDTO trainingClassDTO) {
        trainingClassDTO.setClassCode(generateClassCode(trainingClassDTO));
        trainingClassDTO.setPeriod(getPeriod(trainingClassDTO.getStartTime(),trainingClassDTO.getEndTime()));
        TrainingClass entity = TrainingClassMapper.INSTANCE.toEntity(trainingClassDTO);
        TrainingClass trainingClass = trainingClassRepository.save(entity);
        if (trainingClass != null) {
            return trainingClass.getId();
        }
        return null;
    }

    public String generateClassCode(TrainingClassDTO trainingClassDTO) {

        final Map<Long, String> attendeeCode = new HashMap<>();
        attendeeCode.put(1L,"FR");
        attendeeCode.put(2L,"FR.F.ON");
        attendeeCode.put(3L,"FR.F.OFF");
        attendeeCode.put(4L,"IN");

        String locationName = locationRepository.findById(trainingClassDTO.getLocationId()).orElseThrow().getLocationName();
        String locationCode = locationName.chars()
                .filter(Character::isUpperCase)
                .mapToObj(c -> String.valueOf((char)c))
                .collect(Collectors.joining());
        String programName = trainingProgramRepository.findById(trainingClassDTO.getTrainingProgramId()).orElseThrow().getName();
        String programCode = programName.split(" ", 2)[0];
        Year yearCode = Year.now().minusYears(2000);
        StringBuilder builder = new StringBuilder();
        List<TrainingClass> listExisting = trainingClassRepository.findByClassNameContaining(trainingClassDTO.getClassName());
        String versionCode = String.valueOf(listExisting.size() + 1);

        builder.append(locationCode)
                .append(yearCode)
                .append("_")
                .append(attendeeCode.get(trainingClassDTO.getAttendeeId()))
                .append("_")
                .append(programCode)
                .append("_")
                .append(versionCode);

        return builder.toString();
    }

    public int getPeriod(Time startTime, Time endTime){
        if(startTime.before(Time.valueOf("12:00:00"))){
            return 0;
        }
        if(startTime.after(Time.valueOf("17:00:00"))){
            return 2;
        }
        return 1;
    }
    @Override
    public Page<TrainingClassDTO> getListClass(boolean status,
                                               List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                               List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                               long fsu, long trainerId, String search, String[] sort, Optional<Integer> page)
    {
        List<Sort.Order> order = new ArrayList<>();
        if(sort[0].contains(",")){
            for (String sortItem: sort) {
                String[] subSort = sortItem.split(",");
                order.add(new Sort.Order(getSortDirection(subSort[1]),subSort[0]));
            }
        }else{
            order.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        List<Long> classId = new ArrayList<>();
        if(trainerId!=0){
            classId = trainingClassUnitInformationRepository
                    .findByStatusAndTrainerId( true, trainerId)
                    .stream().map(t -> t.getTrainingClass().getId())
                    .collect(Collectors.toList());
            classId.add(-1L);
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 10, Sort.by(order));
        Page<TrainingClass> pages = trainingClassRepository.getListClass(status, locationId, fromDate, toDate, period,
                isOnline, state, attendeeId, fsu, classId, search, pageable);
        if(pages.getContent().size() > 0){
            return new PageImpl<>(
                    pages.stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList()),
                    pages.getPageable(),
                    pages.getTotalElements());
        }else {
            throw new NotFoundException("Training Class not found!");
        }
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
    private Session getSession(long id, int dayNth) {
        // Get all sessions
        List<Session> sessions = getListUnits(id).stream().map(p -> sessionRepository.findByIdAndStatus(p.getSession().getId(), true).orElseThrow()).toList();

        List<Long> sessionIds = sessions.stream().map(Session::getId).distinct().toList();
        return sessionRepository.findByIdAndStatus(sessionIds.get(dayNth - 1),true).orElseThrow();

//        Map<Integer, Long> list = new HashMap<>();
//        list.put(1, sessions.get(0).getId());
//
//
//        for (int i = 1; i < sessions.size(); i++) {
//            if (sessions.get(i).getId() == sessions.get(i - 1).getId()) {
//                list.put(i + 1, null);
//            } else list.put(i + 1, sessions.get(i).getId());
//        }
//
//        return sessionRepository.findByIdAndStatus(list.get(dayNth), true).orElseThrow();
    }


    // Get list units from a session
    private List<Unit> getListUnitsInASession(long id, int dayNth){
        Session session = getSession(id, dayNth);
        return unitRepository.findBySessionAndStatusOrderByUnitNumber(session, true).orElseThrow();
    }
    public Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    @Override
    public List<TrainingClassDTO> getAllClass() {
        return trainingClassRepository.findAllByStatus(true).stream().map(TrainingClassMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
