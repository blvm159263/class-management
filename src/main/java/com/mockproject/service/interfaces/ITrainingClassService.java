package com.mockproject.service.interfaces;

import com.mockproject.dto.*;

import java.time.LocalDate;

import java.util.List;


public interface ITrainingClassService {
    TrainingClassDTO getAllDetails(long id); // Get all fields from TrainingClass entity by id

    List<UserDTO> getAllTrainers(long id); // Get all Trainers by TrainingClass id

    List<TowerDTO> getAllTowers(long id);  // Get all Towers by TrainingClass id

    AttendeeDTO getAttendee(long id); // Get an Attendee by TrainingClass id

    List<ClassScheduleDTO> getClassSchedule(long id); // Get ClassSchedule by TrainingClass id

    List<UserDTO> getAllAdmins(long id); // Get all Admins by TrainingClass id

    FsuDTO getFsu(long id); // Get Fsu by TrainingClass id

    ContactDTO getContact(long id); // Get Contact by TrainingClass id

    UserDTO getCreator(long id); // Get Creator by TrainingClass id

    Integer getDaysCount(long id, LocalDate targetDate); // Get daysCount by TrainingClass id and Date chosen

    List<UnitDTO> getAllUnitsForADate(long id, LocalDate date); // Get all Units in the class for the Date chosen

    List<UserDTO> getAllTrainersForADate(long id, LocalDate date); // Get all Trainers in the class for the Date chosen

    List<DeliveryTypeDTO> getAllDeliveryTypesForADate(long id, LocalDate date); // Get all DeliveryTypes in the class for the Date chosen

    List<TowerDTO> getAllTowersForADate(long id, LocalDate date); // Get all Towers in the class for the Date chosen

}
