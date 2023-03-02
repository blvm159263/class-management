package com.mockproject.dto.mapper;


import com.mockproject.dto.ContactDTO;
import com.mockproject.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ContactDTOMapper implements Function<Contact, ContactDTO> {


    @Override
    public ContactDTO apply(Contact contact) {
        return new ContactDTO(
                contact.getId(),
                contact.getContactEmail(),
                contact.getContactEmail(),
                contact.isStatus()
        );
    }
}
