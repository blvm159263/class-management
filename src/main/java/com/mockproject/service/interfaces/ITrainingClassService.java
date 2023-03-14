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

    Map<String, Integer> getDaysCount(long id, LocalDate targetDate); // Get daysCount by TrainingClass id and Date chosen

    List<UnitDTO> getAllUnitsForADate(long id, LocalDate date); // Get all Units in the class for the Date chosen

    List<UserDTO> getAllTrainersForADate(long id, LocalDate date); // Get all Trainers in the class for the Date chosen

    List<DeliveryTypeDTO> getAllDeliveryTypesForADate(long id, LocalDate date); // Get all DeliveryTypes in the class for the Date chosen

    List<TowerDTO> getAllTowersForADate(long id, LocalDate date); // Get all Towers in the class for the Date chosen

}
