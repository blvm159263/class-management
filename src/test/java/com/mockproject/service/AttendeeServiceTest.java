package com.mockproject.service;

import com.mockproject.dto.AttendeeDTO;
import com.mockproject.entity.Attendee;
import com.mockproject.repository.AttendeeRepository;
import com.mockproject.repository.TrainingClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AttendeeService.class})
@ExtendWith(SpringExtension.class)
class AttendeeServiceTest {
    @MockBean
    private AttendeeRepository attendeeRepository;
    @MockBean
    private TrainingClassRepository trainingClassRepository;

    @Autowired
    private AttendeeService attendeeService;



    Attendee a1 = new Attendee(1L, "Name 1", "Des 1" , true, null , null);
    Attendee a2 = new Attendee(2L, "Name 2", "Des 2" , false, null , null);
    Attendee a3 = new Attendee(3L, "Name 3", "Des 3" , true, null , null);



    /**
     * Method under test: {@link AttendeeService#save(AttendeeDTO)}
     */
    @Test
    void canSaveAttendee() {
        Attendee attendee = new Attendee();
        when(attendeeRepository.save((Attendee) any())).thenReturn(attendee);
        assertSame(attendee, attendeeService
                .save(new AttendeeDTO(1L, "Attendee Name", "The characteristics of someone or something", true)));
        verify(attendeeRepository).save((Attendee) any());
    }

    /**
     * Method under test: {@link AttendeeService#listAllTrue()}
     */
    @Test
    void canListAllAttendeeWithStatusTrue() {
//        when(attendeeRepository.findByStatus(true)).thenReturn(new ArrayList<>());
//        assertTrue(attendeeService.listAllTrue().isEmpty());
//        verify(attendeeRepository).findByStatus(true);

        List<Attendee> attendees = new ArrayList<>();
        attendees.add(a1);
        attendees.add(a2);
        attendees.add(a3);

        when(attendeeRepository.findByStatus(true)).thenReturn(attendees.stream().filter(Attendee::isStatus).toList());

        List<AttendeeDTO> result = attendeeService.listAllTrue();
        assertEquals(2, result.size());
        assertEquals("Name 1", result.get(0).getAttendeeName());
        assertTrue(result.stream().filter(p-> !p.isStatus()).toList().isEmpty());
        verify(attendeeRepository).findByStatus(true);
    }
}

