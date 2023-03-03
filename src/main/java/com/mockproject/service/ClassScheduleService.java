package com.mockproject.service;

import com.mockproject.dto.ClassScheduleDTO;
import com.mockproject.dto.mapper.ClassScheduleDTOMapper;
import com.mockproject.repository.ClassScheduleRepository;
import com.mockproject.service.interfaces.IClassScheduleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ClassScheduleService implements IClassScheduleService{

    private final ClassScheduleRepository repository;

    private final ClassScheduleDTOMapper mapper;
}
