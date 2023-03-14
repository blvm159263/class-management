package com.mockproject.service.interfaces;

import com.mockproject.dto.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;


public interface ITrainingClassService {
    TrainingClassDTO getAllDetails(long id);

    List<UserDTO> getAllTrainers(long id);

    List<TowerDTO> getAllTowers(long id);

    AttendeeDTO getAttendee(long id);

    List<ClassScheduleDTO> getClassSchedule(long id);

    List<UserDTO> getAllAdmins(long id);

    FsuDTO getFsu(long id);

    ContactDTO getContact(long id);

    UserDTO getCreator(long id);

    Map<String, Integer> getDaysCount(long id, LocalDate targetDate);

    List<UnitDTO> getAllUnitsForADate(long id, LocalDate date);

    List<UserDTO> getAllTrainersForADate(long id, LocalDate date);

    List<DeliveryTypeDTO> getAllDeliveryTypes(long id);

    List<TowerDTO> getAllTowersForADate(long id, LocalDate date);

}
