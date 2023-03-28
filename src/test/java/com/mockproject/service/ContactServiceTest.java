package com.mockproject.service;

import com.mockproject.dto.ContactDTO;
import com.mockproject.entity.Contact;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    Contact contact1 = new Contact(1L, "contacmail1@gmail.com", "Des 1", true, null);
    Contact contact2 = new Contact(2L, "contacmail2@gmail.com", "Des 2", false, null);
    Contact contact3 = new Contact(3L, "contacmail3@gmail.com", "Des 3", true, null);

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
}

