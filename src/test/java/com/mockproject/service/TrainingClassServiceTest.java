package com.mockproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.*;
import com.mockproject.entity.Location;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import com.mockproject.exception.entity.EntityNotFoundException;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.*;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.repository.LocationRepository;
import com.mockproject.repository.TrainingClassAdminRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.TrainingProgramRepository;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TrainingClassService.class})
@ExtendWith(SpringExtension.class)
class TrainingClassServiceTest {
    @MockBean
    private LocationRepository locationRepository;
    @MockBean
    private TrainingProgramRepository trainingProgramRepository;
    @MockBean
    private TrainingClassRepository trainingClassRepository;
    @MockBean
    private TrainingClassMapper trainingClassMapper;
    @MockBean
    private ClassScheduleRepository classScheduleRepository;
    @MockBean
    private TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;
    @MockBean
    private TrainingClassAdminRepository trainingClassAdminRepository;

    @Autowired
    private TrainingClassService trainingClassService;

    Attendee attendee = new Attendee(1L, "", "", true, null, null);
    TrainingProgram trainingProgram = new TrainingProgram(1L, "C# for beginner", LocalDate.now(), LocalDate.now(), BigDecimal.TEN,
            30, true, null, null, null, null);
    Location location = new Location(1L, "Ha Noi", "123 Le Loi", true, null, null);
    Fsu fsu = new Fsu(1L, "", "", true, null);
    Contact contact = new Contact(1L, "", "", true, null);
    User user = new User(1L, "", "", "", "", 1, LocalDate.now(), "", true,
            true, null, null, null, null, null, null,
            null, null, null, null, null,
            null, null, null);


    /**
     * Method under test: {@link TrainingClassService#create(TrainingClassDTO)}
     */
    @Test
    @Disabled
    void canCreateNewTrainingClass() {
        TrainingClass tcAfterSave = new TrainingClass(1L, "Class Name 1", " Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("11:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), 0, true, attendee, trainingProgram, location, fsu,
                contact, user, user, user, user, null, null, null);

        TrainingClassDTO dto = new TrainingClassDTO(null, "Class Name 1", " Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("11:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), 0, true, location.getId(), attendee.getId(), trainingProgram.getId(), fsu.getId(),
                contact.getId(), user.getId(), user.getId(), user.getId(), user.getId());

        when(trainingClassRepository.save(any())).thenReturn(tcAfterSave);

        Long result = trainingClassService.create(dto);
        assertEquals(1L, result);
        verify(trainingClassRepository).save(any());
    }

    /**
     * Method under test: {@link TrainingClassService#create(TrainingClassDTO)}
     */
    @Test
    @Disabled
    void canNotCreateNewTrainingClassIfReferenceObjectNotExist() {

        TrainingClassDTO dto = new TrainingClassDTO(null, "Class Name 1", " Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("12:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), 0, true, location.getId(), attendee.getId(), trainingProgram.getId(), fsu.getId(),
                contact.getId(), user.getId(), user.getId(), 7L, user.getId());

        when(trainingClassRepository.save(any())).thenReturn(null);

        Long result = trainingClassService.create(dto);
        assertNull(result);
        verify(trainingClassRepository).save(any());
    }

    /**
     * Method under test: {@link TrainingClassService#deleteTrainingClass(Long)}
     */
    @Test
    void canDeleteTrainingClass() {
        when(trainingClassRepository.save((TrainingClass) any())).thenReturn(new TrainingClass());
        when(trainingClassRepository.findById((Long) any())).thenReturn(Optional.of(new TrainingClass()));
        assertTrue(trainingClassService.deleteTrainingClass(1L));
        verify(trainingClassRepository).save((TrainingClass) any());
        verify(trainingClassRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TrainingClassService#deleteTrainingClass(Long)}
     */
    @Test
    void itShouldThrowIfCanNotFindTrainingClassById() {
        when(trainingClassRepository.save((TrainingClass) any()))
                .thenThrow(new EntityNotFoundException("An error occurred"));
        when(trainingClassRepository.findById((Long) any())).thenReturn(Optional.of(new TrainingClass()));
        assertThrows(EntityNotFoundException.class, () -> trainingClassService.deleteTrainingClass(1L));
        verify(trainingClassRepository).save((TrainingClass) any());
        verify(trainingClassRepository).findById((Long) any());
    }




}

