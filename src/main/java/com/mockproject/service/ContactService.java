package com.mockproject.service;

import com.mockproject.dto.ContactDTO;
import com.mockproject.entity.Contact;
import com.mockproject.entity.TrainingClass;
import com.mockproject.mapper.ContactMapper;
import com.mockproject.repository.ContactRepository;
import com.mockproject.repository.TrainingClassRepository;
import com.mockproject.service.interfaces.IContactService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService implements IContactService {
    private final ContactRepository repository;

    private final TrainingClassRepository trainingClassRepository;
    @Override
    public ContactDTO getContactById(long id) {
        TrainingClass trainingClass = trainingClassRepository.findByIdAndStatus(id, true).orElseThrow();
        return ContactMapper.INSTANCE.toDTO(trainingClass.getContact());
    }
}
