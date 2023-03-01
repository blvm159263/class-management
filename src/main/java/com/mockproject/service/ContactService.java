package com.mockproject.service;

import com.mockproject.service.interfaces.IContactService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactService implements IContactService {
}
