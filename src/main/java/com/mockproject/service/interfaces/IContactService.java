package com.mockproject.service.interfaces;

import com.mockproject.dto.ContactDTO;

public interface IContactService {
    ContactDTO getContactById(long id);
}
