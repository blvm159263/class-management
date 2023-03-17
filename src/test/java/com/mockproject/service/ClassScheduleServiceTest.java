package com.mockproject.service;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.TrainingClassFilterMap;
import com.mockproject.repository.ClassScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Autowired
    private ClassScheduleService classScheduleService;

    ClassSchedule cs1 = new ClassSchedule(1L, LocalDate.now(), true, null);
    ClassSchedule cs2 = new ClassSchedule(2L, LocalDate.now(), false, null);
    ClassSchedule cs3 = new ClassSchedule(3L, LocalDate.now(), true, null);


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

}