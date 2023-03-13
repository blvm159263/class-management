package com.mockproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.*;
import com.mockproject.entity.Location;
import com.mockproject.entity.TrainingClass;
import com.mockproject.entity.TrainingProgram;
import com.mockproject.entity.User;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.LocationRepository;
import com.mockproject.repository.TrainingClassRepository;
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
    void canCreateNewTrainingClass() {
        TrainingClass tcAfterSave = new TrainingClass(1L, "Class Name 1", " Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("12:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), true, attendee, trainingProgram, location, fsu,
                contact, user, user, user, user, null, null, null);

        TrainingClassDTO dto = new TrainingClassDTO(null, "Class Name 1", " Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("12:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), true, location.getId(), attendee.getId(), trainingProgram.getId(), fsu.getId(),
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
    void canNotCreateNewTrainingClassIfReferenceObjectNotExist() {

        TrainingClassDTO dto = new TrainingClassDTO(null, "Class Name 1", " Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("12:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(), true, location.getId(), attendee.getId(), trainingProgram.getId(), fsu.getId(),
                contact.getId(), user.getId(), user.getId(), 7L, user.getId());

        when(trainingClassRepository.save(any())).thenReturn(null);

        Long result = trainingClassService.create(dto);
        assertNull(result);
        verify(trainingClassRepository).save(any());
    }

    /**
     * Method under test: {@link TrainingClassService#generateClassCode(TrainingClassDTO)}
     */
    @Test
    void canGenerateClassCode() {
        when(trainingClassRepository.findByClassNameContaining((String) any())).thenReturn(new ArrayList<>());

        Location location = new Location();
        location.setLocationName("FR");
        Optional<Location> ofResult = Optional.of(location);
        when(locationRepository.findById((Long) any())).thenReturn(ofResult);
        LocalDate dateCreated = LocalDate.ofEpochDay(1L);
        LocalDate lastDateModified = LocalDate.ofEpochDay(1L);
        BigDecimal hour = BigDecimal.valueOf(42L);
        User creator = new User();
        User lastModifier = new User();
        ArrayList<TrainingClass> listTrainingClasses = new ArrayList<>();
        when(trainingProgramRepository.findById((Long) any()))
                .thenReturn(Optional.of(new TrainingProgram(1L, "FR", dateCreated, lastDateModified, hour, 1, true, creator,
                        lastModifier, listTrainingClasses, new ArrayList<>())));
        trainingClassService.generateClassCode(new TrainingClassDTO());
        verify(trainingClassRepository).findByClassNameContaining((String) any());
        verify(locationRepository).findById((Long) any());
        verify(trainingProgramRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TrainingClassService#generateClassCode(TrainingClassDTO)}
     */
    @Test
    void canGenerateClassCode2() {
        when(trainingClassRepository.findByClassNameContaining((String) any())).thenReturn(new ArrayList<>());

        Location location = new Location();
        location.setLocationName(" ");
        Optional<Location> ofResult = Optional.of(location);
        when(locationRepository.findById((Long) any())).thenReturn(ofResult);
        LocalDate dateCreated = LocalDate.ofEpochDay(1L);
        LocalDate lastDateModified = LocalDate.ofEpochDay(1L);
        BigDecimal hour = BigDecimal.valueOf(42L);
        User creator = new User();
        User lastModifier = new User();
        ArrayList<TrainingClass> listTrainingClasses = new ArrayList<>();
        when(trainingProgramRepository.findById((Long) any()))
                .thenReturn(Optional.of(new TrainingProgram(1L, "FR", dateCreated, lastDateModified, hour, 1, true, creator,
                        lastModifier, listTrainingClasses, new ArrayList<>())));
        trainingClassService.generateClassCode(new TrainingClassDTO());
        verify(trainingClassRepository).findByClassNameContaining((String) any());
        verify(locationRepository).findById((Long) any());
        verify(trainingProgramRepository).findById((Long) any());
    }


}

