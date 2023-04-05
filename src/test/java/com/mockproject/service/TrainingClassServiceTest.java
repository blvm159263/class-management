package com.mockproject.service;


import com.mockproject.dto.TrainingClassDTO;
import com.mockproject.entity.*;
import com.mockproject.mapper.TrainingClassMapper;
import com.mockproject.repository.LocationRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.repository.TrainingClassUnitInformationRepository;
import com.mockproject.repository.TrainingProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private TrainingClassUnitInformationRepository trainingClassUnitInformationRepository;

    @Autowired
    private TrainingClassService trainingClassService;

    //Create Attendee
    Attendee attendee = new Attendee(1L, "Fresher", "Hello", true, null, null);

    //Create Training Program
    TrainingProgram trainingProgram = new TrainingProgram(1L, 1, "C# for beginner", LocalDate.now(), LocalDate.now(), BigDecimal.TEN,
            30, true, null, null, null, null);

    //Create Location
    Location location = new Location(1L, "Ha Noi", "123 Le Loi", true, null, null);

    //Create FSU
    Fsu fsu = new Fsu(1L, "FHM", "STS", true, null);

    //Create Contact
    Contact contact = new Contact(1L, "contact@gmail.com", "Contact email trainer", true, null);

    //Create User
    User user = new User(1L, "user@gmail.com", "1", "user1", "", 1, LocalDate.now(), "", true,
            true, null, null, null, null, null, null,
            null, null, null, null, null,
            null, null, null);

    //Create Training Class
    TrainingClass trainingClass = new TrainingClass(1L, "Class Name 1", "Code113", LocalDate.now(),
                Time.valueOf("09:00:00"), Time.valueOf("11:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
                LocalDate.now(), LocalDate.now(), LocalDate.now(),0 , true, attendee, trainingProgram, location, fsu,
                contact, user, user, user, user, null, null, null);


//    /**
//     * Method under test: {@link TrainingClassService#create(TrainingClassDTO)}
//     */
//    @Test
//    @Disabled
//    void canCreateNewTrainingClass() {
//        TrainingClass tcAfterSave = new TrainingClass(1L, "Class Name 1", " Code113", LocalDate.now(),
//                Time.valueOf("09:00:00"), Time.valueOf("11:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
//                LocalDate.now(), LocalDate.now(), LocalDate.now(),0 , true, attendee, trainingProgram, location, fsu,
//                contact, user, user, user, user, null, null, null);
//
//        TrainingClassDTO dto = new TrainingClassDTO(null, "Class Name 1", " Code113", LocalDate.now(),
//                Time.valueOf("09:00:00"), Time.valueOf("11:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
//                LocalDate.now(), LocalDate.now(), LocalDate.now(), 0, true, location.getId(), attendee.getId(), trainingProgram.getId(), fsu.getId(),
//                contact.getId(), user.getId(), user.getId(), user.getId(), user.getId());
//
//        when(trainingClassRepository.save(any())).thenReturn(tcAfterSave);
//
//        Long result = trainingClassService.create(dto);
//        assertEquals(1L, result);
//        verify(trainingClassRepository).save(any());
//    }
//
//    /**
//     * Method under test: {@link TrainingClassService#create(TrainingClassDTO)}
//     */
//    @Test
//    @Disabled
//    void canNotCreateNewTrainingClassIfReferenceObjectNotExist() {
//
//        TrainingClassDTO dto = new TrainingClassDTO(null, "Class Name 1", " Code113", LocalDate.now(),
//                Time.valueOf("09:00:00"), Time.valueOf("12:00:00"), BigDecimal.ONE, 10, 4, 5, 6, "1", LocalDate.now(),
//                LocalDate.now(), LocalDate.now(), LocalDate.now(),0, true, location.getId(), attendee.getId(), trainingProgram.getId(), fsu.getId(),
//                contact.getId(), user.getId(), user.getId(), 7L, user.getId());
//
//        when(trainingClassRepository.save(any())).thenReturn(null);
//
//        Long result = trainingClassService.create(dto);
//        assertNull(result);
//        verify(trainingClassRepository).save(any());
//    }

    /**
     * Method under test: {@link TrainingClassService#getTrainingClassByClassId(Long)}
     */
    @Test
    void canGetTrainingClassByClassId() {

        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.of(trainingClass));

        TrainingClassDTO result = trainingClassService.getTrainingClassByClassId(trainingClass.getId());

        assertEquals(1L, result.getId());
        assertEquals("Class Name 1", result.getClassName());
        assertEquals("Code113", result.getClassCode());
        assertTrue(result.isStatus());

        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

    @Test
    void itShouldThrowExceptionWhenNotFoundTrainingClass() {
        when(trainingClassRepository.findByIdAndStatus(1L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> trainingClassService.getTrainingClassByClassId(1L));
        verify(trainingClassRepository).findByIdAndStatus(1L, true);
    }

}

