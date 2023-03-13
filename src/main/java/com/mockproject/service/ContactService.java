package com.mockproject.service;

import com.mockproject.repository.ContactRepository;
import com.mockproject.service.interfaces.IContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService implements IContactService {
    private final ContactRepository repository;

}
