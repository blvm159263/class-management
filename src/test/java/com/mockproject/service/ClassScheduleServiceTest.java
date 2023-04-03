package com.mockproject.service;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassFilterMap;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.repository.TrainingClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ClassScheduleService.class})
@ExtendWith(SpringExtension.class)
class ClassScheduleServiceTest {
    @MockBean
    private ClassScheduleRepository classScheduleRepository;
    @MockBean
    private TrainingClassService trainingClassService;
    @MockBean
    private TrainingClassUnitInformationService trainingClassUnitInformationService;
    @MockBean
    private TrainingClassFilterMap trainingClassFilterMap;
    @MockBean
    private TrainingClassRepository trainingClassRepository;

    @Autowired
    private ClassScheduleService classScheduleService;

    //Create Training Class
    TrainingClass tc1 = new TrainingClass(1L, "Class Name 1", "TC1", null, null,
            null, null, 12, 30, 30, 25, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, null, null, null, null, null,
            null, null, null);

    TrainingClass tc2 = new TrainingClass(2L, "Class Name 2", "TC2", null, null,
            null, null, 10, 20, 20, 20, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, null, null, null, null, null,
            null, null, null);

    TrainingClass tc3 = new TrainingClass(3L, "Class Name 3", "TC3", null, null,
            null, null, 10, 20, 20, 20, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, null, null, null, null, null,
            null, null, null);

    //Create Class Schedule
    ClassSchedule cs1 = new ClassSchedule(1L, LocalDate.now(), true, tc1);
    ClassSchedule cs2 = new ClassSchedule(2L, LocalDate.now(), false, tc2);
    ClassSchedule cs3 = new ClassSchedule(3L, LocalDate.now(), true, tc3);
    ClassSchedule cs4 = new ClassSchedule(4L, LocalDate.now(), true, tc1);


    /**
     * Method under test: {@link ClassScheduleService#listAll()}
     */
    @Test
    void canListAllClassSchedule() {
        List<ClassSchedule> list = new ArrayList<>();
        list.add(cs1);
        list.add(cs2);
        list.add(cs3);

        when(classScheduleRepository.findAll()).thenReturn(list);
        List<ClassScheduleDTO> result = classScheduleService.listAll();
        assertEquals(result.size(), 3);
        assertEquals(result.get(0).getId(), 1L);
        verify(classScheduleRepository).findAll();

    }

    /**
     * Method under test: {@link ClassScheduleService#saveClassScheduleForTrainingClass(List, Long)}
     */

    LocalDate date1 = LocalDate.now();
    LocalDate date2 = LocalDate.now();
    LocalDate date3 = LocalDate.now();
    LocalDate date4 = LocalDate.now();

    @Test
    void itShouldReturnTrueWhenListOfScheduleSavedSuccessful() {

        Long tcId = 1L;
        TrainingClass tc = new TrainingClass();
        tc.setId(tcId);
        List<LocalDate> list = new ArrayList<>();
        list.add(date1);
        list.add(date2);
        list.add(date3);
        list.add(date4);
        List<ClassSchedule> listScheduleBefore = list.stream().map(p -> new ClassSchedule(null, p, true, tc)).toList();
        AtomicReference<Long> index = new AtomicReference<>(0L);
        List<ClassSchedule> listScheduleAfter = list.stream().map(p -> {
            index.updateAndGet(v -> v + 1);
            return new ClassSchedule(index.get() + 1, p, true, tc);
        }).toList();

        when(classScheduleRepository.saveAll(listScheduleBefore)).thenReturn(listScheduleAfter);
        assertTrue(classScheduleService.saveClassScheduleForTrainingClass(list, 1L));
        verify(classScheduleRepository).saveAll(listScheduleBefore);
    }


    @Test
    void itShouldReturnFalseWhenListOfScheduleSavedFail() {

        List<LocalDate> list = new ArrayList<>();
        list.add(date1);
        list.add(date2);
        list.add(date3);
        list.add(date4);
        List<ClassSchedule> listScheduleBefore = list.stream().map(p -> new ClassSchedule(null, p, true, new TrainingClass())).toList();


        when(classScheduleRepository.saveAll(listScheduleBefore)).thenReturn(Collections.emptyList());
        assertFalse(classScheduleService.saveClassScheduleForTrainingClass(list, null));
        verify(classScheduleRepository).saveAll(listScheduleBefore);
    }

    /**
     * Method under test: {@link ClassScheduleService#getClassScheduleByTrainingClassId(Long)}
     */
    @Test
    void canGetListClassScheduleByTrainingClassId() {

        //Create list class schedule
        List<ClassSchedule> classSchedules = new ArrayList<>();
        classSchedules.add(cs1);
        classSchedules.add(cs2);
        classSchedules.add(cs3);
        classSchedules.add(cs4);

        //Create training class Id
        Long trainingClassId = 1L;

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));

        when(classScheduleRepository.findByTrainingClassAndStatus(tc1, true))
                .thenReturn(classSchedules.stream().filter(p -> p.isStatus() &&
                        p.getTrainingClass().getId() == tc1.getId()).toList());

        List<ClassScheduleDTO> result = classScheduleService.getClassScheduleByTrainingClassId(tc1.getId());

        assertThrows(NoSuchElementException.class,
                () -> classScheduleService.getClassScheduleByTrainingClassId(4L),
                "Can't find any training class with id is 4");
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(LocalDate.now(), result.get(0).getDate());
        assertEquals(1L, result.get(0).getTrainingClassId());
        assertTrue(result.stream().filter(p -> !p.isStatus()).toList().isEmpty());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
        verify(classScheduleRepository).findByTrainingClassAndStatus(tc1, true);
    }
}