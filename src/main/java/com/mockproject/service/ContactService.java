package com.mockproject.service;

import com.mockproject.dto.mapper.ContactDTOMapper;
import com.mockproject.repository.ContactRepository;
import com.mockproject.service.interfaces.IContactService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ContactService implements IContactService {
    private final ContactRepository repository;

    private final ContactDTOMapper mapper;
}
