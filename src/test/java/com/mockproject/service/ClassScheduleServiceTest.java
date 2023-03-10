package com.mockproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.entity.ClassSchedule;
import com.mockproject.repository.ClassScheduleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClassScheduleService.class})
@ExtendWith(SpringExtension.class)
class ClassScheduleServiceTest {
    @MockBean
    private ClassScheduleRepository classScheduleRepository;

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
    @Test
    void canSaveClassScheduleForTrainingClass() {
        when(classScheduleRepository.saveAll((Iterable<ClassSchedule>) any())).thenReturn(new ArrayList<>());
        assertFalse(classScheduleService.saveClassScheduleForTrainingClass(new ArrayList<>(), 1L));
        verify(classScheduleRepository).saveAll((Iterable<ClassSchedule>) any());
    }

    /**
     * Method under test: {@link ClassScheduleService#saveClassScheduleForTrainingClass(List, Long)}
     */
    @Test
    void canSaveClassScheduleForTrainingClass2() {
        ArrayList<ClassSchedule> classScheduleList = new ArrayList<>();
        classScheduleList.add(new ClassSchedule());
        when(classScheduleRepository.saveAll((Iterable<ClassSchedule>) any())).thenReturn(classScheduleList);
        assertTrue(classScheduleService.saveClassScheduleForTrainingClass(new ArrayList<>(), 1L));
        verify(classScheduleRepository).saveAll((Iterable<ClassSchedule>) any());
    }

    /**
     * Method under test: {@link ClassScheduleService#saveClassScheduleForTrainingClass(List, Long)}
     */
    @Test
    void canSaveClassScheduleForTrainingClass3() {
        when(classScheduleRepository.saveAll((Iterable<ClassSchedule>) any())).thenReturn(new ArrayList<>());

        ArrayList<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(LocalDate.ofEpochDay(1L));
        assertFalse(classScheduleService.saveClassScheduleForTrainingClass(localDateList, 1L));
        verify(classScheduleRepository).saveAll((Iterable<ClassSchedule>) any());
    }

    /**
     * Method under test: {@link ClassScheduleService#saveClassScheduleForTrainingClass(List, Long)}
     */
    @Test
    void canSaveClassScheduleForTrainingClass4() {
        when(classScheduleRepository.saveAll((Iterable<ClassSchedule>) any())).thenReturn(new ArrayList<>());

        ArrayList<LocalDate> localDateList = new ArrayList<>();
        localDateList.add(LocalDate.ofEpochDay(1L));
        localDateList.add(LocalDate.ofEpochDay(1L));
        assertFalse(classScheduleService.saveClassScheduleForTrainingClass(localDateList, 1L));
        verify(classScheduleRepository).saveAll((Iterable<ClassSchedule>) any());
    }


}

