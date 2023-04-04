package com.mockproject.service;

import com.mockproject.dto.ContactDTO;
import com.mockproject.entity.Contact;
import com.mockproject.entity.TrainingClass;
import com.mockproject.repository.ContactRepository;
import com.mockproject.repository.TrainingClassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ContactService.class})
@ExtendWith(SpringExtension.class)
class ContactServiceTest {
    @MockBean
    private ContactRepository contactRepository;
    @MockBean
    private TrainingClassRepository trainingClassRepository;


    @Autowired
    private ContactService contactService;


    //Create Contact
    Contact contact1 = new Contact(1L, "contacmail1@gmail.com", "Des 1", true, null);
    Contact contact2 = new Contact(2L, "contacmail2@gmail.com", "Des 2", false, null);
    Contact contact3 = new Contact(3L, "contacmail3@gmail.com", "Des 3", true, null);

    //Create Training Class
    TrainingClass tc1 = new TrainingClass(1L, "Class Name 1", "TC1", null, null,
            null, null, 12, 30, 30, 25, "Planning", null,
            null, null, null, 1, true, null, null,
            null, null, contact1, null, null, null, null,
            null, null, null);
    /**
     * Method under test: {@link ContactService#listAllTrue()}
     */
    @Test
    void canListAllContactWithStatusTrue() {
        List<Contact> list = new ArrayList<>();
        list.add(contact1);
        list.add(contact2);
        list.add(contact3);

        when(contactRepository.findByStatus(true)).thenReturn(list.stream().filter(Contact::isStatus).toList());

        List<ContactDTO> result = contactService.listAllTrue();

        assertEquals(2, result.size());
        assertEquals("contacmail1@gmail.com",result.get(0).getContactEmail());
        assertTrue(result.stream().filter(p-> !p.isStatus()).toList().isEmpty() );

        verify(contactRepository).findByStatus(true);
    }

    /**
     * Method under test: {@link ContactService#getContactByTrainingClassId(Long)}
     */
    @Test
    void canGetContactByTrainingClassId() {

        Long trainingClassId = 1L;

        when(trainingClassRepository.findByIdAndStatus(trainingClassId, true))
                .thenReturn(Optional.of(tc1));

        ContactDTO result = contactService.getContactByTrainingClassId(tc1.getId());
        assertEquals(1L, result.getId());
        assertEquals("contacmail1@gmail.com", result.getContactEmail());
        assertEquals("Des 1", result.getDescription());
        assertTrue(result.isStatus());

        verify(trainingClassRepository).findByIdAndStatus(trainingClassId, true);
    }

    @Test
    void itShouldThrowExceptionWhenNotFoundTrainingClassId() {
        when(trainingClassRepository.findByIdAndStatus(2L, true))
                .thenReturn(Optional.empty());
        assertThrows(Exception.class, () ->
            contactService.getContactByTrainingClassId(2L)
        );
        verify(trainingClassRepository).findByIdAndStatus(2L, true);
    }

}

