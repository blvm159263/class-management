package com.mockproject.service.interfaces;

import org.springframework.data.domain.Page;
import com.mockproject.dto.*;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

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





    Page<TrainingClassDTO> getListClass(boolean status,
                                        List<Long> locationId, LocalDate fromDate, LocalDate toDate,
                                        List<Integer> period, String isOnline, String state, List<Long> attendeeId,
                                        long fsu, long trainerId, String search, String[] sort, Optional<Integer> page);

    List<TrainingClassDTO> getAllClass();

    Long create(TrainingClassDTO trainingClassDTO);

}
