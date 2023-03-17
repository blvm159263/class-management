package com.mockproject.service;

import com.mockproject.dto.ContactDTO;
import com.mockproject.mapper.ContactMapper;
import com.mockproject.repository.ContactRepository;
import com.mockproject.service.interfaces.IContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService implements IContactService {

    private final ContactRepository repository;

    @Override
    public List<ContactDTO> listAllTrue() {
        return repository.findByStatus(true).stream().map(ContactMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
