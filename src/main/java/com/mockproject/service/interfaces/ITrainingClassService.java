package com.mockproject.service.interfaces;

import com.mockproject.dto.*;

import java.time.LocalDate;

import java.util.List;


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

    Integer getShortDetails(long id, LocalDate targetDate);

    DeliveryTypeDTO getDeliveryType(long id);


}
