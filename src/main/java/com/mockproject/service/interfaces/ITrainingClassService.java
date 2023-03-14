package com.mockproject.service.interfaces;

import com.mockproject.dto.*;

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

    List<DeliveryTypeDTO> getAllDeliveryTypes(long id);

    List<UnitDTO> getAllUnitsForADate(long id, int dayNth);

    List<UserDTO> getAllTrainersForADate(long id, int dayNth);

    List<TowerDTO> getAllTowersForADate(long id, int dayNth);


}
