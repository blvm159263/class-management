package com.mockproject.service.interfaces;

import com.mockproject.dto.TrainingClassDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.mockproject.dto.*;

import java.time.LocalDate;

import java.util.List;


public interface ITrainingClassService {

    Page<TrainingClassDTO> getListClass(boolean status,
                                        List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                        List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                        long fsu, long trainerId, String search, String[] sort, Optional<Integer> page);

    List<TrainingClassDTO> getAllClass();

    Long create(TrainingClassDTO trainingClassDTO);


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
